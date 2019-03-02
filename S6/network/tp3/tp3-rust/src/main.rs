extern crate exitfailure;
extern crate failure;
extern crate net2;
extern crate structopt;

use failure::Error;
use net2::{TcpBuilder, UdpBuilder};
use std::io::{prelude::*, BufRead};
use std::net::{
    IpAddr, Ipv4Addr, Ipv6Addr, SocketAddr, TcpListener, TcpStream, ToSocketAddrs, UdpSocket,
};
use std::str::FromStr;
use structopt::StructOpt;

fn main() -> Result<(), exitfailure::ExitFailure> {
    let opt = Opt::from_args();

    let bind_addr: IpAddr = match opt.ip_stack {
        IpStack::IpV4 => Ipv4Addr::UNSPECIFIED.into(),
        IpStack::IpV6 { .. } => Ipv6Addr::UNSPECIFIED.into(),
    };

    match opt.protocol {
        Protocol::TCP => {
            let s = match opt.ip_stack {
                IpStack::IpV4 => TcpBuilder::new_v4()?,
                IpStack::IpV6 { only } => {
                    let s = TcpBuilder::new_v6()?;
                    s.only_v6(only)?;
                    s
                }
            };

            match opt.mode {
                Mode::Client { server } => tcp_client(
                    s.reuse_address(true)?
                        .bind((bind_addr, 0))?
                        .connect(server)?,
                )?,
                Mode::Server { port } => {
                    tcp_server(s.reuse_address(true)?.bind((bind_addr, port))?.listen(1)?)?
                }
            }
        }
        Protocol::UDP => {
            let s = match opt.ip_stack {
                IpStack::IpV4 => UdpBuilder::new_v4()?,
                IpStack::IpV6 { only } => {
                    let s = UdpBuilder::new_v6()?;
                    s.only_v6(only)?;
                    s
                }
            };

            match opt.mode {
                Mode::Client { server } => {
                    udp_client(s.reuse_address(true)?.bind((bind_addr, 0))?, server)?
                }
                Mode::Server { port } => {
                    udp_server(s.reuse_address(true)?.bind((bind_addr, port))?)?
                }
            }
        }
    }

    Ok(())
}

fn tcp_client(mut stream: TcpStream) -> Result<(), Error> {
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

fn tcp_server(listener: TcpListener) -> Result<(), Error> {
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

fn udp_client(socket: UdpSocket, server: String) -> Result<(), Error> {
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

fn udp_server(socket: UdpSocket) -> Result<(), Error> {
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

#[derive(StructOpt, Debug)]
#[structopt(author = "Loïc Escales <loic.escales@etu.univ-amu.fr>")]
struct Opt {
    /// tcp or udp
    protocol: Protocol,

    /// 6 or 6only or 4
    ip_stack: IpStack,

    #[structopt(flatten)]
    mode: Mode,
}

#[derive(StructOpt, Debug)]
enum Mode {
    #[structopt(name = "client")]
    /// Se connecte à un serveur.
    Client {
        #[structopt(name = "host_name>:<port")]
        /// L'adresse IP et le port du serveur sur lequel se connecter.
        server: String,
    },
    #[structopt(name = "server")]
    /// Créé un serveur.
    Server {
        /// Le port sur lequel le serveur écoute.
        port: u16,
    },
}

#[derive(Debug)]
enum Protocol {
    TCP,
    UDP,
}

impl FromStr for Protocol {
    type Err = &'static str;

    fn from_str(s: &str) -> Result<Self, Self::Err> {
        match s {
            "tcp" => Ok(Protocol::TCP),
            "udp" => Ok(Protocol::UDP),
            _ => Err("unknown protocol"),
        }
    }
}

#[derive(Debug)]
enum IpStack {
    IpV6 { only: bool },
    IpV4,
}

impl FromStr for IpStack {
    type Err = &'static str;

    fn from_str(s: &str) -> Result<Self, Self::Err> {
        match s {
            "6" => Ok(IpStack::IpV6 { only: false }),
            "6only" => Ok(IpStack::IpV6 { only: true }),
            "4" => Ok(IpStack::IpV4),
            _ => Err("unknown ip stack"),
        }
    }
}
