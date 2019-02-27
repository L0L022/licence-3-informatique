extern crate exitfailure;
extern crate failure;
extern crate structopt;

use failure::Error;
use std::io::prelude::*;
use std::io::BufRead;
use std::net::{TcpListener, TcpStream};
use structopt::StructOpt;

fn main() -> Result<(), exitfailure::ExitFailure> {
    match Opt::from_args() {
        Opt::Client { server } => Ok(client(server)?),
        Opt::Server { port } => Ok(server(port)?),
    }
}

fn client(server: String) -> Result<(), Error> {
    let mut stream = TcpStream::connect(server)?;

    println!("client ok");

    let stdin = std::io::stdin();
    let mut input_stream = stdin.lock();
    let mut line = String::new();

    while input_stream.read_line(&mut line)? > 0 {
        stream.write(line.as_ref())?;
        line.clear();
    }

    Ok(())
}

fn server(port: u16) -> Result<(), Error> {
    let listener = TcpListener::bind(("::", port))?;

    println!("server ok");

    let (mut stream, _) = listener.accept()?;
    let mut buff = [0; 101];
    buff[0] = b'>';

    println!("client connected");

    loop {
        let amt = stream.read(&mut buff[1..])?;
        let buff = &buff[..=amt];
        print!("{}", String::from_utf8_lossy(buff));
        stream.write(buff)?;

        if &buff[..] == b">shutdown\n" {
            break;
        }
    }

    Ok(())
}

#[derive(StructOpt)]
#[structopt(author = "Loïc Escales <loic.escales@etu.univ-amu.fr>")]
pub enum Opt {
    #[structopt(name = "client")]
    /// Se connecte à un serveur TCP.
    Client {
        #[structopt(name = "host_name>:<port")]
        /// L'adresse IP et le port du serveur sur lequel se connecter.
        server: String,
    },
    #[structopt(name = "server")]
    /// Créé un serveur TCP.
    Server {
        /// Le port sur lequel le serveur écoute.
        port: u16,
    },
}
