# tp2-rust

Un client et un serveur UDP écrit en Rust.

## Compilation

D'abord il faut installer [cargo](https://rustup.rs/), puis :

```bash
# Build
cd tp2-rust
cargo build --release

# Run
cargo run --release -- -h
```

## Les options

```
USAGE:
    tp2-rust <SUBCOMMAND>

FLAGS:
    -h, --help       Prints help information
    -V, --version    Prints version information

SUBCOMMANDS:
    client    Se connecte à un serveur UDP.
    help      Prints this message or the help of the given subcommand(s)
    server    Créé un serveur UDP.
```

### Lancer le serveur

```bash
cargo run --release -- server 12345
```

### Lancer le client

```bash
cargo run --release -- client localhost:12345
```
