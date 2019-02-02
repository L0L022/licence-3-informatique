use logos::Logos;

// TODO : ajout fonction de création de fichier lex

pub struct Lexer<'input> {
	lexer: logos::Lexer<LogosToken, &'input str>,
}

impl<'input> Lexer<'input> {
	pub fn new(input: &'input str) -> Self {
		Lexer {
			lexer: LogosToken::lexer(input)
		}
	}
}

#[derive(Debug)]
pub enum LexicalError {
    ReadError(std::ops::Range<usize>),
	ParseNumber(std::ops::Range<usize>, std::num::ParseIntError),
}

pub type Spanned<Tok, Loc, Error> = Result<(Loc, Tok, Loc), Error>;

impl<'input> Iterator for Lexer<'input> {
	type Item = Spanned<Token, usize, LexicalError>;

	fn next(&mut self) -> Option<Self::Item> {
		loop {
			use LogosToken::*;

			let logos_token = self.lexer.token;
            let range = self.lexer.range();

			return match logos_token {
				End => None,
				Error => Some(Err(LexicalError::ReadError(range))),
				Comment => {
					self.lexer.advance();
					continue
				},
				_ => {
					use Token as T;

					let token = match logos_token {
						End => unreachable!(),
						Error => unreachable!(),
						Number => {
							match self.lexer.slice().parse::<i32>() {
								Ok(v) => T::Number(v),
								Err(e) => return Some(Err(LexicalError::ParseNumber(range, e))),
							}
						},
						Id => T::Id(self.lexer.slice().to_string()),
						Comment => unreachable!(),
						Comma => T::Comma,
						Semicolon => T::Semicolon,
                        IntegerType => T::IntegerType,
                        ReadFunction => T::ReadFunction,
                        WriteFunction => T::WriteFunction,
						Return => T::Return,
						If => T::If,
						Then => T::Then,
						Else => T::Else,
						While => T::While,
						Do => T::Do,
						OpenParenthesis => T::OpenParenthesis,
						CloseParenthesis => T::CloseParenthesis,
						OpenCurlyBracket => T::OpenCurlyBracket,
						CloseCurlyBracket => T::CloseCurlyBracket,
						OpenSquareBracket => T::OpenSquareBracket,
						CloseSquareBracket => T::CloseSquareBracket,
						Addition => T::Addition,
						Subtraction => T::Subtraction,
						Multiplication => T::Multiplication,
						Division => T::Division,
						LessThan => T::LessThan,
						Equal => T::Equal,
						And => T::And,
						Or => T::Or,
						Not => T::Not,
					};

					self.lexer.advance();
					Some(Ok((range.start, token, range.end)))
				}
			}
		}
	}
}

// TODO : ajout méthodes name et value

#[derive(Debug, PartialEq, Clone)]
pub enum Token {
	Number(i32),
	Id(String),
	Comma,
	Semicolon,

	// Types

    IntegerType,

    // Predefined functions
    ReadFunction,
    WriteFunction,

	// Instructions

	Return,
	If,
	Then,
	Else,
	While,
	Do,

	// Brackets

	OpenParenthesis,
	CloseParenthesis,
	OpenCurlyBracket,
	CloseCurlyBracket,
	OpenSquareBracket,
	CloseSquareBracket,

	// Operators

	Addition,
	Subtraction,
	Multiplication,
	Division,
	LessThan,
	Equal,
	And,
	Or,
	Not,
}

use std::fmt;

impl fmt::Display for Token {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        use Token::*;

        if let Number(n) = self {
            return write!(f, "nombre\t{}", n);
        }

        let (name, value) : (&str, &str) = match self {
            Number(_) => unreachable!(),
            Id(id) => ("identificateur", id),
            Comma => ("symbole", "VIRGULE"),
            Semicolon => ("symbole", "POINT_VIRGULE"),

            // Types

            IntegerType => ("mot_clef", "entier"),

            // Predefined functions
            ReadFunction => ("mot_clef", "lire"),
            WriteFunction => ("mot_clef", "ecrire"),

            // Instructions

            Return => ("mot_clef", "retour"),
            If => ("mot_clef", "si"),
            Then => ("mot_clef", "alors"),
            Else => ("mot_clef", "sinon"),
            While => ("mot_clef", "tantque"),
            Do => ("mot_clef", "faire"),

            // Brackets

            OpenParenthesis => ("symbole", "PARENTHESE_OUVRANTE"),
            CloseParenthesis => ("symbole", "PARENTHESE_FERMANTE"),
            OpenCurlyBracket => ("symbole", "ACCOLADE_OUVRANTE"),
            CloseCurlyBracket => ("symbole", "ACCOLADE_FERMANTE"),
            OpenSquareBracket => ("symbole", "CROCHET_OUVRANT"),
            CloseSquareBracket => ("symbole", "CROCHET_FERMANT"),

            // Operators

            Addition => ("symbole", "PLUS"),
            Subtraction => ("symbole", "MOINS"),
            Multiplication => ("symbole", "FOIS"),
            Division => ("symbole", "DIVISE"),
            LessThan => ("symbole", "INFERIEUR"),
            Equal => ("symbole", "EGAL"),
            And => ("symbole", "ET"),
            Or => ("symbole", "OU"),
            Not => ("symbole", "NON"),
        };

        write!(f, "{}\t{}", name, value)
    }
}

#[derive(Logos, Debug, PartialEq, Copy, Clone)]
enum LogosToken {
	#[end]
	End,

	#[error]
	Error,

	#[regex = "[0-9]+"]
	Number,

	#[regex = "[a-zA-Z_$][a-zA-Z_$0-9]*"]
	Id,

	#[regex = "#.*"]
	Comment,

	#[token = ","]
	Comma,

	#[token = ";"]
	Semicolon,

    // Types

    #[token = "entier"]
    IntegerType,

    // Predefined functions

    #[token =  "lire"]
    ReadFunction,

    #[token = "ecrire"]
    WriteFunction,

	// Instructions

	#[token = "retour"]
	Return,

	#[token = "si"]
	If,

	#[token = "alors"]
	Then,

	#[token = "sinon"]
	Else,

	#[token = "tantque"]
	While,

	#[token = "faire"]
	Do,

	// Brackets

	#[token = "("]
	OpenParenthesis,

	#[token = ")"]
	CloseParenthesis,

	#[token = "{"]
	OpenCurlyBracket,

	#[token = "}"]
	CloseCurlyBracket,

	#[token = "["]
	OpenSquareBracket,

	#[token = "]"]
	CloseSquareBracket,

	// Operators

	#[token = "+"]
	Addition,

	#[token = "-"]
	Subtraction,

	#[token = "*"]
	Multiplication,

	#[token = "/"]
	Division,

	#[token = "<"]
	LessThan,

	#[token = "="]
	Equal,

	#[token = "&"]
	And,

	#[token = "|"]
	Or,

	#[token = "!"]
	Not,
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn affect_err() {
        test_l_file("affect-err");
    }

    #[test]
    fn affect() {
        test_l_file("affect");
    }

    #[test]
    fn boucle() {
        test_l_file("boucle");
    }

    #[test]
    fn expression() {
        test_l_file("expression");
    }

    #[test]
    fn max() {
        test_l_file("max");
    }

    #[test]
    fn tri() {
        test_l_file("tri");
    }

    fn test_l_file(filename: &str) {
        // lire fichier .l
        let file_content = std::fs::read(format!("tests/resources/{}.l", filename)).unwrap();
        let content = &String::from_utf8_lossy(&file_content);
        // analyser le programme
        let l = Lexer::new(&content);
        // lire la réponse

        let mut my_lex = String::new();

        for spanned in l {
            let (begin, token, end) = spanned.unwrap();
            let line = format!("{}\t{}\n", &content[begin..end], token);
            my_lex.push_str(&line);
        }
        // comparer la réponse

        let good_file_content = std::fs::read(format!("tests/resources/{}.lex", filename)).unwrap();
        let good_content = &String::from_utf8_lossy(&good_file_content);

        assert_eq!(&good_content[..], &my_lex[..]);
    }
}
