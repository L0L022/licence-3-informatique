# tp3-rust

Un client et un serveur TCP et UDP écrit en Rust.

## Compilation

D'abord il faut installer [cargo](https://rustup.rs/), puis :

```bash
# Build
cd tp3-rust
cargo build --release

# Run
cargo run --release -- -h
```

## Les options

```
USAGE:
    tp3-rust <protocol> <ip_stack> <SUBCOMMAND>

FLAGS:
    -h, --help       Prints help information
    -V, --version    Prints version information

ARGS:
    <protocol>    tcp ou udp
    <ip_stack>    6 ou 6only ou 4

SUBCOMMANDS:
    client    Se connecte à un serveur.
    help      Prints this message or the help of the given subcommand(s)
    server    Créé un serveur.
```

### Lancer le serveur TCP avec double pile

```bash
cargo run --release -- tcp 6 server 12345
```

### Lancer le client TCP avec double pile

```bash
cargo run --release -- tcp 6 client localhost:12345
```

### Lancer le serveur UDP avec double pile

```bash
cargo run --release -- udp 6 server 12345
```

### Lancer le client UDP avec double pile

```bash
cargo run --release -- udp 6 client localhost:12345
```
