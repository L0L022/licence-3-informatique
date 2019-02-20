#!/bin/bash

PORT=12345

# le dossier contenant les fichiers .class
JAVA_CLASS_DIR="/home/loic/Code/licence-3-informatique/S6/network/tp2/tp2-java/target/classes"

# l'exécutable généré par `cargo build`
RUST_EXEC="/tmp/tp2-target/debug/tp2-rust"

java_client="java EchoUDPClient localhost $PORT"
java_server="java EchoUDPServer $PORT"

rust_client="$RUST_EXEC client localhost:$PORT"
rust_server="$RUST_EXEC server $PORT"

test() {
    mess="très long texte\npetit texte\nshutdown\n"
    server_right_mess="server ok\n>très long texte\n>petit texte\n>shutdown\n"

    res="$(mktemp)"
    ${!server} > $res &

    sleep 0.5

    ${!client} <<< "$(echo -en "$mess")" > /dev/null

    sleep 0.5

    echo -n "$client $server "
    
    if [ "$(echo -en "$server_right_mess")" == "$(cat $res)" ]
    then
        echo "ok"
    else
        echo "error"
        echo "server log:"
        cat $res
        echo
    fi

    rm $res
}

cd "$JAVA_CLASS_DIR"

client=rust_client; server=rust_server; test
client=rust_client; server=java_server; test
client=java_client; server=rust_server; test
client=java_client; server=java_server; test
