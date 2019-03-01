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

CREATE OR REPLACE FUNCTION EstElement(p_NomAtt VARCHAR, p_NumEnsAtt INTEGER) RETURN INTEGER IS
BEGIN
    IF INSTR(EnsAtt2Chaine(p_NumEnsAtt), p_NomAtt) = 0 THEN
        RETURN 0;
    ELSE
        RETURN 1;
    END IF;
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

CREATE OR REPLACE FUNCTION CreerDF(p_ChaineAtt VARCHAR) RETURN INTEGER IS
pos INTEGER;
l_NumDF INTEGER;
NumEnsGauche INTEGER;
NumEnsDroit INTEGER;
BEGIN
    IF p_ChaineAtt IS NULL THEN
        RETURN 0;
    END IF;
    
    pos := INSTR(p_ChaineAtt, '->');
    
    NumEnsGauche := CreerEnsAtt(SUBSTR(p_ChaineAtt, 1, pos - 1));
    NumEnsDroit := CreerEnsAtt(SUBSTR(p_ChaineAtt, pos + 2));

    INSERT INTO DFs VALUES(NumDF.NextVal, NumEnsGauche, NumEnsDroit) RETURNING NumDF INTO l_NumDF;
    RETURN l_NumDF;
END;
/

CREATE OR REPLACE FUNCTION DF2Chaine(p_NumDF INTEGER) RETURN VARCHAR IS
BEGIN    
    FOR row IN (SELECT * FROM DFs WHERE NumDF = p_NumDF) LOOP
        RETURN EnsAtt2Chaine(row.NumEnsGauche) || '->' || EnsAtt2Chaine(row.NumEnsDroit);
    END LOOP;
END;
/

CREATE OR REPLACE FUNCTION EstTriviale(p_NumDF INTEGER) RETURN INTEGER IS
BEGIN
    FOR row IN (SELECT * FROM DFs WHERE NumDF = p_NumDF) LOOP
        RETURN EstInclus(row.NumEnsDroit, row.NumEnsGauche);
    END LOOP;
END;
/

CREATE OR REPLACE FUNCTION EstPlusForte(p_NumDF1 INTEGER, p_NumDF2 INTEGER) RETURN INTEGER IS
BEGIN
    FOR row1 IN (SELECT * FROM DFs WHERE NumDF = p_NumDF1) LOOP
        FOR row2 IN (SELECT * FROM DFs WHERE NumDF = p_NumDF2) LOOP
            IF EstInclus(row2.NumEnsDroit, row1.NumEnsDroit) = 1 AND EstInclus(row1.NumEnsGauche, row2.NumEnsGauche) = 1 THEN
                RETURN 1;
            ELSE
                RETURN 0;
            END IF;
        END LOOP;
    END LOOP;
END;
/

CREATE OR REPLACE FUNCTION CreerEnsDFVide RETURN INTEGER IS
var INTEGER;
BEGIN
    INSERT INTO EnsemblesDFs VALUES(NumEnsDF.NextVal) RETURNING NumEnsDF INTO var;
    RETURN var;
END;
/

CREATE OR REPLACE PROCEDURE AjouterDF(p_NumDF INTEGER, p_NumEnsDF INTEGER) IS
BEGIN
    INSERT INTO EnsembleContientDF VALUES(p_NumEnsDF, p_NumDF);
END;
/

CREATE OR REPLACE FUNCTION CreerEnsDF(p_ChaineDF VARCHAR) RETURN INTEGER IS
NumEnsDF INTEGER;
posB INTEGER;
posE INTEGER;
BEGIN
    NumEnsDF := CreerEnsDFVide();
    
    IF p_ChaineDF IS NULL THEN
        RETURN NumEnsDF;
    END IF;

    posB := 1;
    posE := 1;
    LOOP
        posE := INSTR(p_ChaineDF, ';', posB);

        IF posE = 0 THEN
            EXIT;
        END IF;
        
        AjouterDF(CreerDF(SUBSTR(p_ChaineDF, posB, posE - posB)), NumEnsDF);
        
        posB := posE + 1;
    END LOOP;

    AjouterDF(CreerDF(SUBSTR(p_ChaineDF, posB)), NumEnsDF);

    RETURN NumEnsDF;
END;
/

CREATE OR REPLACE FUNCTION EnsDF2Chaine(p_NumEnsDF INTEGER) RETURN VARCHAR IS
chaine VARCHAR(100);
BEGIN

    FOR row IN (SELECT NumDF FROM EnsembleContientDF WHERE NumEnsDF = p_NumEnsDF ORDER BY NumDF) LOOP
        IF chaine IS NULL THEN
            chaine := DF2Chaine(row.NumDF);
        ELSE
            chaine := chaine || ';' || DF2Chaine(row.NumDF);
        END IF;
    END LOOP;

    RETURN chaine;
END;
/

CREATE OR REPLACE FUNCTION EnsDF2EnsAtt(p_NumEnsDF INTEGER) RETURN INTEGER IS
NumEnsAtt INTEGER;
BEGIN
    NumEnsAtt := CreerEnsAttVide();
    
    FOR row IN (SELECT DISTINCT NomAtt
                FROM EnsembleContientAtribut ECS
                INNER JOIN DFs D ON ECS.NumEnsAtt = D.NumEnsGauche OR ECS.NumEnsAtt = D.NumEnsDroit
                INNER JOIN EnsembleContientDF ECDF ON D.NumDF = ECDF.NumDF)
    LOOP
        AjouterAtt(row.NomAtt, NumEnsAtt);
    END LOOP;
    
    RETURN NumEnsAtt;
END;
/

CREATE OR REPLACE FUNCTION CopieEnsDF(p_NumEnsDF INTEGER) RETURN INTEGER IS
BEGIN
    RETURN CreerEnsDF(EnsDF2Chaine(p_NumEnsDF));
END;
/

CREATE OR REPLACE FUNCTION CreerSchema(p_ChaineEnsAtt VARCHAR, p_ChaineEnsDF VARCHAR) RETURN INTEGER IS
var INTEGER;
BEGIN
    var := CreerEnsDF(p_ChaineEnsDF);
    INSERT INTO Schemas VALUES(NumSchema.NextVal, UnionAtt(CreerEnsAtt(p_ChaineEnsAtt), EnsDF2EnsAtt(var)), var) RETURNING NumSchema INTO var;
    RETURN var;
END;
/

-- SELECT EnsAtt2Chaine(NumEnsAtt), EnsDF2Chaine(NumEnsDF) FROM Schemas WHERE NumSchema = &p_NumSchema;

CREATE OR REPLACE FUNCTION EnsClef2Chaine(p_NumEnsClef INTEGER) RETURN VARCHAR IS
chaine VARCHAR(100);
BEGIN
    FOR row IN (SELECT NumClef FROM EnsembleContientClef WHERE NumEnsClef = p_NumEnsClef ORDER BY NumEnsClef) LOOP
        IF chaine IS NULL THEN
            chaine := '{' || row.NumClef || '}';
        ELSE
            chaine := chaine || ',' || '{' || row.NumClef || '}';
        END IF;
    END LOOP;

    RETURN chaine;
END;
/

CREATE OR REPLACE FUNCTION CreerEnsClefVide(p_NumSchema INTEGER) RETURN INTEGER IS
var INTEGER;
BEGIN
    INSERT INTO EnsemblesClefs VALUES(NumEnsClef.NextVal, p_NumSchema) RETURNING NumEnsClef INTO var;
    RETURN var;
END;
/

CREATE OR REPLACE PROCEDURE AjouterClef(p_NumClef INTEGER, p_NumEnsClef INTEGER) IS
BEGIN
    INSERT INTO EnsembleContientClef VALUES(p_NumEnsClef, p_NumClef);
END;
/

CREATE OR REPLACE FUNCTION CreerStructureVide RETURN INTEGER IS
var INTEGER;
BEGIN
    INSERT INTO Structures VALUES(NumStructure.NextVal) RETURNING NumStructures INTO var;
    RETURN var;
END;
/

CREATE OR REPLACE PROCEDURE AjouterSchema(p_NumSchema INTEGER, p_NumStructure INTEGER) IS
BEGIN
    INSERT INTO StructureContientSchema VALUES(p_NumStructure, p_NumSchema);
END;
/