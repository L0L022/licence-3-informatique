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
atts VARCHAR;
BEGIN

    FOR att IN (SELECT NomAtt FROM EnsembleContientAtribut ORDER BY NomAtt) LOOP
        IF atts IS NULL THEN
            atts := att;
        ELSE
            atts := atts || ',' || att;
        END IF;
    END LOOP;

    RETURN atts;
END;
/

SELECT * FROM EnsemblesAttributs;
SELECT * FROM EnsembleContientAtribut;