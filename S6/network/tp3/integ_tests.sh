#!/bin/bash

PORT=12345

# le dossier contenant les fichiers .class
JAVA_CLASS_DIR="/home/loic/Code/licence-3-informatique/S6/network/tp3/tp3-java/target/classes"

# l'exécutable généré par `cargo build`
RUST_EXEC="/tmp/target/debug/tp3-rust"

java_client="java EchoTCPClient localhost $PORT"
java_server="java EchoTCPServer $PORT"

rust_client="$RUST_EXEC tcp 4 client localhost:$PORT"
rust_server="$RUST_EXEC tcp 4 server $PORT"

test() {
    mess="shutdown\n"
    server_right_mess="server ok\nclient connected\n>shutdown\n"

    res="$(mktemp)"
    ${!server} > $res 2> /dev/null &

    sleep 0.5

    ${!client} <<< "$(echo -en "$mess")" > /dev/null

    sleep 0.5

    echo -n "$client $server "
    
    if [ "$(echo -en "$server_right_mess")" == "$(cat $res)" ]
    then
        echo "ok"
    else
        echo "error"
        echo "### server log:"
        cat $res
        echo "###"
    fi

    rm $res
}

cd "$JAVA_CLASS_DIR"

client=rust_client; server=rust_server; test
client=rust_client; server=java_server; test
client=java_client; server=rust_server; test
client=java_client; server=java_server; test
