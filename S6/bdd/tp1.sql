SET SERVEROUTPUT ON

---------------------------------- question 1.1 -------------------------------------------------------------------------------
DROP TABLE EnsembleContientAtribut PURGE;
DROP TABLE EnsemblesAttributs PURGE;


CREATE TABLE EnsemblesAttributs (
    NumEnsAtt INTEGER NOT NULL PRIMARY KEY
);

CREATE TABLE EnsembleContientAtribut(
    NumEnsAtt INTEGER NOT NULL,
    NomAtt VARCHAR(30) NOT NULL,
    CONSTRAINT PK_EnsAtt PRIMARY KEY (NumEnsAtt, NomAtt),
    CONSTRAINT FK_EnsAtt_NumEnsAtt FOREIGN KEY(NumEnsAtt) REFERENCES EnsemblesAttributs(NumEnsAtt) ON DELETE CASCADE
);

drop sequence NumEnsAtt;
create sequence NumEnsAtt;

---------------------------------- question 1.2 -------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION CreerEnsAttVide
RETURN INTEGER IS
var INTEGER;
BEGIN
    INSERT INTO EnsemblesAttributs VALUES(NumEnsAtt.NextVal) RETURNING NumEnsAtt INTO var;
    RETURN var;
END;
/

CREATE OR REPLACE PROCEDURE AjouterAtt(p_NomAtt VARCHAR, p_NumEnsAtt NUMBER)
      IS
      BEGIN
          INSERT INTO EnsembleContientAtribut VALUES(p_NumEnsAtt, p_NomAtt);
      END AjouterAtt;
/

Variable Num1 INTEGER

BEGIN :Num1 := CreerEnsAttVide();
END;
/

--Execute AjouterAtt('Z', 1);

SELECT * FROM EnsemblesAttributs;
SELECT * FROM EnsembleContientAtribut;

CREATE OR REPLACE FUNCTION CreerEnsAtt(p_ChaineAtt VARCHAR) RETURN INTEGER IS
NumEnsAtt INTEGER;
posB INTEGER;
posE INTEGER;
BEGIN
    NumEnsAtt := CreerEnsAttVide();
    
    IF p_chaineAtt IS NULL THEN
        RETURN NumEnsAtt;
    END IF;

    posB := 1;
    posE := 1;
    LOOP
        posE := INSTR(p_ChaineAtt, ',', posB);

        IF posE = 0 THEN
            EXIT;
        END IF;
        
        AjouterAtt(SUBSTR(p_ChaineAtt, posB, posE - posB), NumEnsAtt);
        
        posB := posE + 1;
    END LOOP;

    AjouterAtt(SUBSTR(p_ChaineAtt, posB), NumEnsAtt);

    RETURN NumEnsAtt;
END;
/

--Variable Num2 INTEGER
declare num2 INTEGER;
BEGIN Num2 := CreerEnsAtt('A,V,C');
END;
/

CREATE OR REPLACE FUNCTION EnsAtt2Chaine(p_NumEnsAtt INTEGER) RETURN VARCHAR IS
atts VARCHAR(100);
BEGIN

    FOR row IN (SELECT NomAtt FROM EnsembleContientAtribut WHERE NumEnsAtt = p_NumEnsAtt ORDER BY NomAtt) LOOP
        IF atts IS NULL THEN
            atts := row.NomAtt;
        ELSE
            atts := atts || ',' || row.NomAtt;
        END IF;
    END LOOP;

    RETURN atts;
END;
/

declare atts VARCHAR(100);
BEGIN atts := EnsAtt2Chaine(37); DBMS_OUTPUT.PUT_LINE(atts);
END;
/

CREATE OR REPLACE FUNCTION EstElement(p_NomAtt VARCHAR, p_NumEnsAtt INTEGER) RETURN INTEGER IS
BEGIN
    IF INSTR(EnsAtt2Chaine(p_NumEnsAtt), p_NomAtt) = 0 THEN
        RETURN 0;
    ELSE
        RETURN 1;
    END IF;
END;
/

declare num3 INTEGER;
BEGIN Num3 := EstElement('D', 37); DBMS_OUTPUT.PUT_LINE(Num3);
END;
/

CREATE OR REPLACE FUNCTION EstInclus(p_NumEnsAtt_petit INTEGER, p_NumEnsAtt_grand INTEGER) RETURN INTEGER IS
BEGIN
    FOR row IN (SELECT NomAtt FROM EnsembleContientAtribut WHERE NumEnsAtt = p_NumEnsAtt_petit ORDER BY NomAtt) LOOP
        IF EstElement(row.NomAtt, p_NumEnsAtt_grand) = 0 THEN
            RETURN 0;
        END IF;
    END LOOP;
    RETURN 1;
END;
/

CREATE OR REPLACE FUNCTION EstEgal(p_NumEnsAtt_1 INTEGER, p_NumEnsAtt_2 INTEGER) RETURN INTEGER IS
BEGIN
    IF EnsAtt2Chaine(p_NumEnsAtt_1) = EnsAtt2Chaine(p_NumEnsAtt_2) THEN
        RETURN 1;
    ELSE
        RETURN 0;
    END IF;
END;
/

CREATE OR REPLACE FUNCTION UnionAtt(p_NumEnsAtt_1 INTEGER, p_NumEnsAtt_2 INTEGER) RETURN INTEGER IS
NumEnsAtt INTEGER;
BEGIN
    NumEnsAtt := CreerEnsAttVide();

    FOR row IN (SELECT DISTINCT NomAtt FROM EnsembleContientAtribut WHERE NumEnsAtt = p_NumEnsAtt_1 OR NumEnsAtt = p_NumEnsAtt_2) LOOP
        AjouterAtt(row.NomAtt, NumEnsAtt);
    END LOOP;
    
    RETURN NumEnsAtt;
END;
/

CREATE OR REPLACE FUNCTION IntersectionAtt(p_NumEnsAtt_1 INTEGER, p_NumEnsAtt_2 INTEGER) RETURN INTEGER IS
NumEnsAtt INTEGER;
BEGIN
    NumEnsAtt := CreerEnsAttVide();

    FOR row IN (SELECT NomAtt FROM EnsembleContientAtribut WHERE NumEnsAtt = p_NumEnsAtt_1
                INTERSECT
                SELECT NomAtt FROM EnsembleContientAtribut WHERE NumEnsAtt = p_NumEnsAtt_2)
    LOOP
        AjouterAtt(row.NomAtt, NumEnsAtt);
    END LOOP;
    
    RETURN NumEnsAtt;
END;
/

CREATE OR REPLACE FUNCTION SoustractionAtt(p_NumEnsAtt_1 INTEGER, p_NumEnsAtt_2 INTEGER) RETURN INTEGER IS
NumEnsAtt INTEGER;
BEGIN
    NumEnsAtt := CreerEnsAttVide();

    FOR row IN (SELECT NomAtt FROM EnsembleContientAtribut WHERE NumEnsAtt = p_NumEnsAtt_1
                MINUS
                SELECT NomAtt FROM EnsembleContientAtribut WHERE NumEnsAtt = p_NumEnsAtt_2)
    LOOP
        AjouterAtt(row.NomAtt, NumEnsAtt);
    END LOOP;
    
    RETURN NumEnsAtt;
END;
/

CREATE OR REPLACE FUNCTION CopieAtt(p_NumEnsAtt INTEGER) RETURN INTEGER IS
NumEnsAtt INTEGER;
BEGIN
    NumEnsAtt := CreerEnsAttVide();

    FOR row IN (SELECT NomAtt FROM EnsembleContientAtribut WHERE NumEnsAtt = p_NumEnsAtt)
    LOOP
        AjouterAtt(row.NomAtt, NumEnsAtt);
    END LOOP;
    
    RETURN NumEnsAtt;
END;
/

SELECT * FROM EnsemblesAttributs;
SELECT * FROM EnsembleContientAtribut;