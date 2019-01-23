// auto-generated: "lalrpop 0.16.3"
// sha256: 59bd8783dc2c215b6072daee20e46221d39533553b2db7feb02adc258705f60
#[allow(unused_extern_crates)]
extern crate lalrpop_util as __lalrpop_util;
#[allow(unused_imports)]
use self::__lalrpop_util::state_machine as __state_machine;

#[cfg_attr(rustfmt, rustfmt_skip)]
mod __parse__Program {
    #![allow(non_snake_case, non_camel_case_types, unused_mut, unused_variables, unused_imports, unused_parens)]

    #[allow(unused_extern_crates)]
    extern crate lalrpop_util as __lalrpop_util;
    #[allow(unused_imports)]
    use self::__lalrpop_util::state_machine as __state_machine;
    use super::__ToTriple;
    #[allow(dead_code)]
    pub enum __Symbol<>
     {
        Variant0(lexer::Token),
        Variant1(Statement),
    }
    const __ACTION: &'static [i8] = &[
        // State 0
        0, 3,
        // State 1
        0, 0,
        // State 2
        4, 0,
        // State 3
        0, 5,
        // State 4
        0, 0,
    ];
    const __EOF_ACTION: &'static [i8] = &[
        // State 0
        0,
        // State 1
        -2,
        // State 2
        0,
        // State 3
        0,
        // State 4
        -1,
    ];
    const __GOTO: &'static [i8] = &[
        // State 0
        2, 0,
        // State 1
        0, 0,
        // State 2
        0, 0,
        // State 3
        0, 0,
        // State 4
        0, 0,
    ];
    fn __expected_tokens(__state: usize) -> Vec<::std::string::String> {
        const __TERMINAL: &'static [&'static str] = &[
            r###""$""###,
            r###""id""###,
        ];
        __ACTION[(__state * 2)..].iter().zip(__TERMINAL).filter_map(|(&state, terminal)| {
            if state == 0 {
                None
            } else {
                Some(terminal.to_string())
            }
        }).collect()
    }
    pub struct __StateMachine<>
    where 
    {
        __phantom: ::std::marker::PhantomData<()>,
    }
    impl<> __state_machine::ParserDefinition for __StateMachine<>
    where 
    {
        type Location = usize;
        type Error = lexer::LexicalError;
        type Token = lexer::Token;
        type TokenIndex = usize;
        type Symbol = __Symbol<>;
        type Success = Statement;
        type StateIndex = i8;
        type Action = i8;
        type ReduceIndex = i8;
        type NonterminalIndex = usize;

        #[inline]
        fn start_location(&self) -> Self::Location {
              Default::default()
        }

        #[inline]
        fn start_state(&self) -> Self::StateIndex {
              0
        }

        #[inline]
        fn token_to_index(&self, token: &Self::Token) -> Option<usize> {
            __token_to_integer(token, ::std::marker::PhantomData::<()>)
        }

        #[inline]
        fn action(&self, state: i8, integer: usize) -> i8 {
            __ACTION[(state as usize) * 2 + integer]
        }

        #[inline]
        fn error_action(&self, state: i8) -> i8 {
            __ACTION[(state as usize) * 2 + (2 - 1)]
        }

        #[inline]
        fn eof_action(&self, state: i8) -> i8 {
            __EOF_ACTION[state as usize]
        }

        #[inline]
        fn goto(&self, state: i8, nt: usize) -> i8 {
            __GOTO[(state as usize) * 2 + nt] - 1
        }

        fn token_to_symbol(&self, token_index: usize, token: Self::Token) -> Self::Symbol {
            __token_to_symbol(token_index, token, ::std::marker::PhantomData::<()>)
        }

        fn expected_tokens(&self, state: i8) -> Vec<String> {
            __expected_tokens(state as usize)
        }

        #[inline]
        fn uses_error_recovery(&self) -> bool {
            false
        }

        #[inline]
        fn error_recovery_symbol(
            &self,
            recovery: __state_machine::ErrorRecovery<Self>,
        ) -> Self::Symbol {
            panic!("error recovery not enabled for this grammar")
        }

        fn reduce(
            &mut self,
            action: i8,
            start_location: Option<&Self::Location>,
            states: &mut Vec<i8>,
            symbols: &mut Vec<__state_machine::SymbolTriple<Self>>,
        ) -> Option<__state_machine::ParseResult<Self>> {
            __reduce(
                action,
                start_location,
                states,
                symbols,
                ::std::marker::PhantomData::<()>,
            )
        }

        fn simulate_reduce(&self, action: i8) -> __state_machine::SimulatedReduce<Self> {
            __simulate_reduce(action, ::std::marker::PhantomData::<()>)
        }
    }
    fn __token_to_integer<
    >(
        __token: &lexer::Token,
        _: ::std::marker::PhantomData<()>,
    ) -> Option<usize>
    {
        match *__token {
            lexer::Token::Dollar if true => Some(0),
            lexer::Token::Id if true => Some(1),
            _ => None,
        }
    }
    fn __token_to_symbol<
    >(
        __token_index: usize,
        __token: lexer::Token,
        _: ::std::marker::PhantomData<()>,
    ) -> __Symbol<>
    {
        match __token_index {
            0 => match __token {
                __tok @ lexer::Token::Dollar => __Symbol::Variant0((__tok)),
                _ => unreachable!(),
            },
            1 => match __token {
                __tok @ lexer::Token::Id => __Symbol::Variant0((__tok)),
                _ => unreachable!(),
            },
            _ => unreachable!(),
        }
    }
    fn __simulate_reduce<
    >(
        __reduce_index: i8,
        _: ::std::marker::PhantomData<()>,
    ) -> __state_machine::SimulatedReduce<__StateMachine<>>
    {
        match __reduce_index {
            0 => {
                __state_machine::SimulatedReduce::Reduce {
                    states_to_pop: 3,
                    nonterminal_produced: 0,
                }
            }
            1 => __state_machine::SimulatedReduce::Accept,
            _ => panic!("invalid reduction index {}", __reduce_index)
        }
    }
    pub struct ProgramParser {
        _priv: (),
    }

    impl ProgramParser {
        pub fn new() -> ProgramParser {
            ProgramParser {
                _priv: (),
            }
        }

        #[allow(dead_code)]
        pub fn parse<
            __TOKEN: __ToTriple<>,
            __TOKENS: IntoIterator<Item=__TOKEN>,
        >(
            &self,
            __tokens0: __TOKENS,
        ) -> Result<Statement, __lalrpop_util::ParseError<usize, lexer::Token, lexer::LexicalError>>
        {
            let __tokens = __tokens0.into_iter();
            let mut __tokens = __tokens.map(|t| __ToTriple::to_triple(t));
            let __r = __state_machine::Parser::drive(
                __StateMachine {
                    __phantom: ::std::marker::PhantomData::<()>,
                },
                __tokens,
            );
            __r
        }
    }
    pub(crate) fn __reduce<
    >(
        __action: i8,
        __lookahead_start: Option<&usize>,
        __states: &mut ::std::vec::Vec<i8>,
        __symbols: &mut ::std::vec::Vec<(usize,__Symbol<>,usize)>,
        _: ::std::marker::PhantomData<()>,
    ) -> Option<Result<Statement,__lalrpop_util::ParseError<usize, lexer::Token, lexer::LexicalError>>>
    {
        let (__pop_states, __nonterminal) = match __action {
            0 => {
                __reduce0(__action, __lookahead_start, __states, __symbols, ::std::marker::PhantomData::<()>)
            }
            1 => {
                // __Program = Program => ActionFn(0);
                let __sym0 = __pop_Variant1(__symbols);
                let __start = __sym0.0.clone();
                let __end = __sym0.2.clone();
                let __nt = super::__action0::<>(__sym0);
                return Some(Ok(__nt));
            }
            _ => panic!("invalid action code {}", __action)
        };
        let __states_len = __states.len();
        __states.truncate(__states_len - __pop_states);
        let __state = *__states.last().unwrap() as usize;
        let __next_state = __GOTO[__state * 2 + __nonterminal] - 1;
        __states.push(__next_state);
        None
    }
    fn __pop_Variant1<
    >(
        __symbols: &mut ::std::vec::Vec<(usize,__Symbol<>,usize)>
    ) -> (usize, Statement, usize)
     {
        match __symbols.pop().unwrap() {
            (__l, __Symbol::Variant1(__v), __r) => (__l, __v, __r),
            _ => panic!("symbol type mismatch")
        }
    }
    fn __pop_Variant0<
    >(
        __symbols: &mut ::std::vec::Vec<(usize,__Symbol<>,usize)>
    ) -> (usize, lexer::Token, usize)
     {
        match __symbols.pop().unwrap() {
            (__l, __Symbol::Variant0(__v), __r) => (__l, __v, __r),
            _ => panic!("symbol type mismatch")
        }
    }
    pub(crate) fn __reduce0<
    >(
        __action: i8,
        __lookahead_start: Option<&usize>,
        __states: &mut ::std::vec::Vec<i8>,
        __symbols: &mut ::std::vec::Vec<(usize,__Symbol<>,usize)>,
        _: ::std::marker::PhantomData<()>,
    ) -> (usize, usize)
    {
        // Program = "id", "$", "id" => ActionFn(1);
        let __sym2 = __pop_Variant0(__symbols);
        let __sym1 = __pop_Variant0(__symbols);
        let __sym0 = __pop_Variant0(__symbols);
        let __start = __sym0.0.clone();
        let __end = __sym2.2.clone();
        let __nt = super::__action1::<>(__sym0, __sym1, __sym2);
        __symbols.push((__start, __Symbol::Variant1(__nt), __end));
        (3, 0)
    }
}
pub use self::__parse__Program::ProgramParser;

fn __action0<
>(
    (_, __0, _): (usize, Statement, usize),
) -> Statement
{
    (__0)
}

fn __action1<
>(
    (_, t, _): (usize, lexer::Token, usize),
    (_, _, _): (usize, lexer::Token, usize),
    (_, i, _): (usize, lexer::Token, usize),
) -> Statement
{
    Statement::DclVariable(t, i)
}

pub trait __ToTriple<> {
    fn to_triple(value: Self) -> Result<(usize,lexer::Token,usize), __lalrpop_util::ParseError<usize, lexer::Token, lexer::LexicalError>>;
}

impl<> __ToTriple<> for (usize, lexer::Token, usize) {
    fn to_triple(value: Self) -> Result<(usize,lexer::Token,usize), __lalrpop_util::ParseError<usize, lexer::Token, lexer::LexicalError>> {
        Ok(value)
    }
}
impl<> __ToTriple<> for Result<(usize, lexer::Token, usize), lexer::LexicalError> {
    fn to_triple(value: Self) -> Result<(usize,lexer::Token,usize), __lalrpop_util::ParseError<usize, lexer::Token, lexer::LexicalError>> {
        match value {
            Ok(v) => Ok(v),
            Err(error) => Err(__lalrpop_util::ParseError::User { error }),
        }
    }
}
