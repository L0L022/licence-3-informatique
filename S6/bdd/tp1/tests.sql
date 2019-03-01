SET SERVEROUTPUT ON

Variable Num1 INTEGER

BEGIN :Num1 := CreerEnsAttVide();
END;
/

--Execute AjouterAtt('Z', 1);

SELECT * FROM EnsemblesAttributs;
SELECT * FROM EnsembleContientAtribut;

declare num2 INTEGER;
BEGIN Num2 := CreerEnsAtt('A,V,C');
END;
/

declare atts VARCHAR(100);
BEGIN atts := EnsAtt2Chaine(37); DBMS_OUTPUT.PUT_LINE(atts);
END;
/

declare res INTEGER;
 num5 INTEGER;
 num6 INTEGER;

BEGIN 
    num5 := CreerEnsAtt('A,B,C');
    num6 := CreerEnsAtt('A,B,C,D,E');
    res := EstInclus(num5,num6 ); DBMS_OUTPUT.PUT_LINE(res);
END;
/

declare res INTEGER;
 num5 INTEGER;
 num6 INTEGER;

BEGIN 
    num5 := CreerEnsAtt('A,B,C');
    num6 := CreerEnsAtt('B,A,C');
    res := EstEgal(num5,num6 ); DBMS_OUTPUT.PUT_LINE(res);
END;
/

declare res INTEGER;
 num5 INTEGER;
 num6 INTEGER;
 num7 INTEGER;

BEGIN 
    num5 := CreerEnsAtt('A,B,C,F');
    num6 := CreerEnsAtt('A,B,C,D,E');
    num7 := CreerEnsAtt('A,B,C,D,E,F');
    res := EstEgal(num7,UnionAtt(num5,num6 )); DBMS_OUTPUT.PUT_LINE(res);
END;
/

declare res INTEGER;
 num5 INTEGER;
 num6 INTEGER;
 num7 INTEGER;

BEGIN 
    num5 := CreerEnsAtt('A,B,C,F');
    num6 := CreerEnsAtt('A,B,C,D,E');
    num7 := CreerEnsAtt('A,B,C');
    res := EstEgal(num7,IntersectionAtt(num5,num6 )); DBMS_OUTPUT.PUT_LINE(res);
END;
/

declare res INTEGER;
 num5 INTEGER;
 num6 INTEGER;
 num7 INTEGER;

BEGIN 
    num5 := CreerEnsAtt('A,B,C,F');
    num6 := CreerEnsAtt('A,B,C,D,E');
    num7 := CreerEnsAtt('F');
    res := EstEgal(num7,SoustractionAtt(num5,num6 )); DBMS_OUTPUT.PUT_LINE(res);
END;
/

declare res INTEGER;
 num5 INTEGER;
 num6 INTEGER;
 num7 INTEGER;

BEGIN 
    num5 := CreerEnsAtt('A,B,C,F');
    res := EstEgal(num5,CopieAtt(num5)); DBMS_OUTPUT.PUT_LINE(res);
END;
/

declare res INTEGER;
 num5 INTEGER;
 num6 INTEGER;
 num7 INTEGER;

BEGIN 
    res := CreerDF('A,B,C->E,D');
END;
/

DECLARE
    df VARCHAR(100);
BEGIN
    df := DF2Chaine(CreerDF('A,B,C->E,D'));
    DBMS_OUTPUT.PUT_LINE(df);
END;
/

DECLARE
    res INTEGER;
BEGIN
    res := EstTriviale(CreerDF('A,B,C->E,D'));
    DBMS_OUTPUT.PUT_LINE(res);
    res := EstTriviale(CreerDF('A,E,D->E,D'));
    DBMS_OUTPUT.PUT_LINE(res);
END;
/

DECLARE
    res INTEGER;
BEGIN
    res := EstPlusForte(CreerDF('A,B->E,D,F'), CreerDF('A,B,C->E,D'));
    DBMS_OUTPUT.PUT_LINE(res); -- 1
    res := EstPlusForte(CreerDF('A,B->E,D'), CreerDF('C->F'));
    DBMS_OUTPUT.PUT_LINE(res); -- 0
END;
/

DECLARE
    res INTEGER;
BEGIN
    res := CreerEnsDF('A->B;B->C');
    DBMS_OUTPUT.PUT_LINE(res);
END;
/

DECLARE
    EnsDF VARCHAR(100);
BEGIN
    EnsDF := EnsDF2Chaine(CreerEnsDF('A->B;B->C;C->D;D->F'));
    DBMS_OUTPUT.PUT_LINE(EnsDF);
END;
/

DECLARE
    res VARCHAR(100);
BEGIN
    res := EnsAtt2Chaine(EnsDF2EnsAtt(CreerEnsDF('A->B;B->C;C->D;D->F;F->G;G->H;H->I')));
    DBMS_OUTPUT.PUT_LINE(res);
END;
/

DECLARE
    res INTEGER;
BEGIN
    res := CreerEnsDF('A->B;B->C');
    DBMS_OUTPUT.PUT_LINE(res);
END;
/

DECLARE
    res INTEGER;
BEGIN
    res := CreerSchema('A,B,C,D', 'A->B;B->C');
    DBMS_OUTPUT.PUT_LINE(res);
    res := CreerSchema('D', 'A->B;B->C');
    DBMS_OUTPUT.PUT_LINE(res);
END;
/

SELECT * FROM EnsemblesAttributs;
SELECT * FROM EnsembleContientAtribut;
SELECT * FROM DFs;
SELECT * FROM EnsemblesDFs;
SELECT * FROM EnsembleContientDF;