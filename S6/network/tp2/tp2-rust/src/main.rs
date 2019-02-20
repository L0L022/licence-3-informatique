extern crate exitfailure;
extern crate failure;
extern crate structopt;

use failure::Error;
use std::io::BufRead;
use std::net::{SocketAddr, ToSocketAddrs, UdpSocket};
use structopt::StructOpt;

fn main() -> Result<(), exitfailure::ExitFailure> {
    match Opt::from_args() {
        Opt::Client { server } => Ok(client(server)?),
        Opt::Server { port } => Ok(server(port)?),
    }
}

fn client(server: String) -> Result<(), Error> {
    let socket = UdpSocket::bind(":::0")?;
    let server: Vec<SocketAddr> = server.to_socket_addrs()?.collect();

    println!("client ok");

    let stdin = std::io::stdin();
    let mut input_stream = stdin.lock();
    let mut line = String::new();

    while input_stream.read_line(&mut line)? > 0 {
        socket.send_to(line.as_ref(), &server[..])?;
        line.clear();
    }

    Ok(())
}

fn server(port: u16) -> Result<(), Error> {
    let socket = UdpSocket::bind(("::", port))?;
    let mut buff = [0; 101];
    buff[0] = b'>';

    println!("server ok");

    loop {
        let (amt, src) = socket.recv_from(&mut buff[1..])?;
        let buff = &buff[..=amt];
        print!("{}", String::from_utf8_lossy(buff));
        socket.send_to(buff, &src)?;

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
    /// Se connecte à un serveur UDP.
    Client {
        #[structopt(name = "host_name>:<port")]
        /// L'adresse IP et le port du serveur sur lequel se connecter.
        server: String,
    },
    #[structopt(name = "server")]
    /// Créé un serveur UDP.
    Server {
        /// Le port sur lequel le serveur écoute.
        port: u16,
    },
}
