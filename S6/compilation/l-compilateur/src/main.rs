use l_compilateur::*;

fn main() {
	let t1 = "
	entier $a, entier $b;
	main()
	{
	$a = 1;
	ecrire(pow(sqrt($pi), 2), $a, $b, f(584));
	}
	";

let t2 = "max( entier $a, entier $b )
{
    si $a < $b alors {
        retour $b;
    }
    retour $a;
}

main()
entier $v_1, entier $v_2;
{
    $v_1 = lire();
    $v_2 = lire();
    si max( $v_1, $v_2 ) = $v_1 alors
    {
        ecrire( $v_1 );
    }
    sinon
    {
        ecrire( $v_2 );
    }

}
";
let t3 =
"
#mon super programme !!!!
# il fait plein de trucs !
###### c'est la folie #####

entier $tab[ 10 ];

initialiser()
{
    $tab[0] = 8;    $tab[1] = 6;    $tab[2] = 9;
    $tab[3] = 9;    $tab[4] = 4;    $tab[5] = 2;
    $tab[6] = 3;    $tab[7] = 1;    $tab[8] = 4;
    $tab[9] = 5;
}

afficher( entier $n )
entier $i;
{
    $i = 0;
    tantque $i < $n faire {
        ecrire( $tab[ $i ] );
        $i = $i + 1;
    }
    ecrire( 0 );
}

# plein de commentaires !

echanger( entier $i, entier $j )
entier $temp;
{#commentaire
    $temp = $tab[ $j ];
    $tab[ $j ] = $tab[ $i ];
    $tab[ $i ] = $temp;
} # un autre comentaire !

trier( entier $n )
entier $echange, entier $j, entier $m;
{
    $m = $n;
    $echange = 1;
    tantque $echange = 1 faire # un petit tant que
    {
        $echange = 0;# on déclare une variable
        $j = 0;
        tantque $j < $m - 1 faire
        {
            si $tab[ $j + 1 ] < $tab[ $j ] alors {
                echanger( $j, $j + 1 );
                $echange = 1; # on a une autre fonction
            }
            $j = $j + 1;
        }
        $m = $m - 1;
    }
}

main()
{
    initialiser();
    afficher(20-10+30*34*1/4+f());
    trier( -10 );
    afficher( -10 + -2 );
}

";

	let t = t3;
	let lexer = lexer::Lexer::new(t);
	use lalrpop_util::ParseError::*;
	match lexical::lexical::ProgramParser::new().parse(lexer) {
		Ok(v) => println!("{:#?}", v),
		Err(e) => match e {
			InvalidToken { location } => println!("{:?}", location),
			UnrecognizedToken { token, expected } => {
				if let Some(token) = token {
					println!("{:?}", &t[token.0 .. token.2]);
					println!("{:?}", token.1);
				} else {
					println!("{:?} {:?}", token, expected)
				}
			},
			ExtraToken { token } => println!("{:?}", token),
			User { error } => println!("{:?}", error),
		}
	}
}
