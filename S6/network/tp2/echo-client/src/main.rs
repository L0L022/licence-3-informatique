use std::net::UdpSocket;

fn main() -> std::io::Result<()> {
    {
        let socket = UdpSocket::bind("localhost:1234")?;

        let mut received_buf = [0; 100];
        let (amt, src) = socket.recv_from(&mut received_buf)?;

        let mut send_buffer = vec![b'>'];

        send_buffer.extend(&received_buf[..amt]);

        socket.send_to(&send_buffer, &src)?;
    }
    Ok(())

}
