use logos::Logos;

#[derive(Logos, Debug, PartialEq, Copy, Clone)]
enum LogosToken {
	#[end]
	End,

	#[error]
	Error,
// entier non négatif
// il faut repenser tout le système
	#[regex = "[0-9]+"]
	Integer,

	#[regex = "[a-zA-Z_][a-zA-Z0-9_-]*"]
	Id,

	#[regex = "#.*"]
	Comment,

	#[token = ","]
	Comma,

	#[token = ";"]
	Semicolon,

	#[token = "$"]
	Dollar,

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

#[derive(Debug, PartialEq, Clone)]
pub enum Token {
	Integer(i32),
	Id(String),
	Comma,
	Semicolon,
	Dollar,

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

#[derive(Debug)]
pub enum LexicalError {
    Error,
	ParseInteger,
}

impl From<std::num::ParseIntError> for LexicalError {
	fn from(error: std::num::ParseIntError) -> Self {
		LexicalError::ParseInteger
	}
}

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

pub type Spanned<Tok, Loc, Error> = Result<(Loc, Tok, Loc), Error>;

impl<'input> Iterator for Lexer<'input> {
	type Item = Spanned<Token, usize, LexicalError>;

	fn next(&mut self) -> Option<Self::Item> {
		loop {
			use LogosToken::*;

			let t = self.lexer.token;
			print!("{:?}, ", t);

			return match t {
				End => None,
				Error => Some(Err(LexicalError::Error)),
				Comment => {
					self.lexer.advance();
					continue
				},
				_ => {
					use Token as T;
					use std::str::FromStr;

					let tt = match t {
						End => unreachable!(),
						Error => unreachable!(),
						Integer => {
							match self.lexer.slice().parse::<i32>() {
								Ok(v) => T::Integer(v),
								Err(e) => return Some(Err(LexicalError::ParseInteger)),
							}
						},
						Id => T::Id(self.lexer.slice().to_string()),
						Comment => unreachable!(),
						Comma => T::Comma,
						Semicolon => T::Semicolon,
						Dollar => T::Dollar,
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

					let r = self.lexer.range();
					self.lexer.advance();
					Some(Ok((r.start, tt, r.end)))
				}
			}
		}
	}
}
