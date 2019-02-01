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

Execute AjouterAtt('A', 1);

SELECT * FROM EnsemblesAttributs;
SELECT * FROM EnsembleContientAtribut;
