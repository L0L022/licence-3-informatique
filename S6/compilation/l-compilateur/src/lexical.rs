use std::convert::TryFrom;

#[derive(Debug)]
pub enum Type {
	Integer
}

impl TryFrom<String> for Type {
	type Error = String;

	fn try_from(value: String) -> Result<Self, Self::Error> {
		match &value[..] {
			"entier" => Ok(Type::Integer),
			_ => Err(format!("unknown type: {}", value)),
		}
	}
}

pub type Id = String;

#[derive(Debug)]
pub enum Variable {
	Scalar(Scalar),
	Vector(Vector),
}

pub type Scalar = (Type, Id);
pub type Vector = (Type, i32, Id);

#[derive(Debug)]
pub enum Statement {
	DclVariable(Vec<Variable>),
	DclFunction(Id, Vec<Scalar>, Vec<Variable>, Vec<Instruction>),
}

#[derive(Debug)]
pub enum Instruction {
	Affectation(LeftValue, Expression),
	Eval(Expression),
	Return(Option<Expression>),
	If(BooleanExpression, Vec<Instruction>, Vec<Instruction>),
	While(BooleanExpression, Vec<Instruction>),
}

#[derive(Debug)]
pub enum LeftValue {
	Variable(Id),
	VariableAt(Id, Box<Expression>),
}

#[derive(Debug)]
pub enum Expression {
	Value(i32),
	LeftValue(LeftValue),
	CallFunction(Id, Vec<Expression>),
	Operation(ArithmeticOperator, Box<Expression>, Box<Expression>),
}

#[derive(Debug)]
pub enum ArithmeticOperator {
	Addidion,
	Subtraction,
	Multiplication,
	Division,
}

#[derive(Debug)]
pub enum BooleanExpression {
	And(Box<BooleanExpression>, Box<BooleanExpression>),
	Or(Box<BooleanExpression>, Box<BooleanExpression>),
	Not(Box<BooleanExpression>),
	Equal(Expression, Expression),
	LessThan(Expression, Expression),
}

lalrpop_mod!(pub lexical);
