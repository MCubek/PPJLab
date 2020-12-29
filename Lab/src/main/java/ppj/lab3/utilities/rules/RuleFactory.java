package ppj.lab3.utilities.rules;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.rules.expressions.AditivniIzraz.AditivniIzrazBez;
import ppj.lab3.utilities.rules.expressions.AditivniIzraz.AditivniIzrazOp;
import ppj.lab3.utilities.rules.expressions.BinIIzraz.BinIIzrazBez;
import ppj.lab3.utilities.rules.expressions.BinIIzraz.BinIIzrazOp;
import ppj.lab3.utilities.rules.expressions.BinIliIzraz.BinIliIzrazBez;
import ppj.lab3.utilities.rules.expressions.BinIliIzraz.BinIliIzrazOp;
import ppj.lab3.utilities.rules.expressions.BinXiliIzraz.BinXiliIzrazBez;
import ppj.lab3.utilities.rules.expressions.BinXiliIzraz.BinXiliIzrazOp;
import ppj.lab3.utilities.rules.expressions.CastIzraz.CastIzrazBez;
import ppj.lab3.utilities.rules.expressions.CastIzraz.CastIzrazSa;
import ppj.lab3.utilities.rules.expressions.ImeTipa.ImeTipaBez;
import ppj.lab3.utilities.rules.expressions.ImeTipa.ImeTipaConst;
import ppj.lab3.utilities.rules.expressions.Izraz.IzrazJedan;
import ppj.lab3.utilities.rules.expressions.Izraz.IzrazVise;
import ppj.lab3.utilities.rules.expressions.IzrazPridruzivanja.IzrazPridruzivanjaBez;
import ppj.lab3.utilities.rules.expressions.IzrazPridruzivanja.IzrazPridruzivanjaOp;
import ppj.lab3.utilities.rules.expressions.JednakosniIzraz.JednakosniIzrazBez;
import ppj.lab3.utilities.rules.expressions.JednakosniIzraz.JednakosniIzrazOp;
import ppj.lab3.utilities.rules.expressions.ListaArgumenata.ListaArgumenataJedan;
import ppj.lab3.utilities.rules.expressions.ListaArgumenata.ListaArgumenataVise;
import ppj.lab3.utilities.rules.expressions.LogIIzraz.LogIIzrazBez;
import ppj.lab3.utilities.rules.expressions.LogIIzraz.LogIIzrazOp;
import ppj.lab3.utilities.rules.expressions.LogIliIzraz.LogIliIzrazBez;
import ppj.lab3.utilities.rules.expressions.LogIliIzraz.LogIliIzrazOp;
import ppj.lab3.utilities.rules.expressions.MultiplikativniIzraz.MultiplikativniIzrazBez;
import ppj.lab3.utilities.rules.expressions.MultiplikativniIzraz.MultiplikativniIzrazOp;
import ppj.lab3.utilities.rules.expressions.OdnosniIzraz.OdnosniIzrazBez;
import ppj.lab3.utilities.rules.expressions.OdnosniIzraz.OdnosniIzrazOp;
import ppj.lab3.utilities.rules.expressions.PostfiksIzraz.*;
import ppj.lab3.utilities.rules.expressions.PrimarniIzraz.*;
import ppj.lab3.utilities.rules.expressions.SpecifikatorTipa.SpecifikatorTipaChar;
import ppj.lab3.utilities.rules.expressions.SpecifikatorTipa.SpecifikatorTipaInt;
import ppj.lab3.utilities.rules.expressions.SpecifikatorTipa.SpecifikatorTipaVoid;
import ppj.lab3.utilities.rules.expressions.UnarniIzraz.UnarniIzrazBez;
import ppj.lab3.utilities.rules.expressions.UnarniIzraz.UnarniIzrazInc;
import ppj.lab3.utilities.rules.expressions.UnarniIzraz.UnarniIzrazOp;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;
import ppj.lab3.utilities.symbols.TerminalSymbol;

import java.util.HashMap;
import java.util.Map;

public class RuleFactory {

    private final Map<SemanticProduction, Object> ruleMap;

    public RuleFactory() {
        ruleMap = new HashMap<>();

        //<primarni_izraz> ::= IDN
        SemanticProduction primarniIzrazIdn= SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<primarni_izraz>"), new TerminalSymbol("IDN",0,new String[0]));
        ruleMap.put(primarniIzrazIdn, new PrimarniIzrazIdn());

        //<primarni_izraz> ::= BROJ
        SemanticProduction primarniIzrazNumber = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<primarni_izraz>"), new TerminalSymbol("BROJ",0, new String[0]));
        ruleMap.put(primarniIzrazNumber, new PrimarniIzrazNumber());

        //<primarni_izraz> ::= ZNAK
        SemanticProduction primarniIzrazSign = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<primarni_izraz>"), new TerminalSymbol("ZNAK",0, new String[0]));
        ruleMap.put(primarniIzrazSign, new PrimarniIzrazSign());

        //<primarni_izraz> ::= NIZ_ZNAKOVA
        SemanticProduction primarniIzrazListSign = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<primarni_izraz>"), new TerminalSymbol("NIZ_ZNAKOVA",0, new String[0]));
        ruleMap.put(primarniIzrazListSign, new PrimarniIzrazListSign());

        //<primarni_izraz> ::= L_ZAGRADA <izraz> D_ZAGRADA
        SemanticProduction primarniIzrazLZagrada = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<primarni_izraz>"), new TerminalSymbol("L_ZAGRADA",0, new String[0]),
                new NonTerminalSymbol("<izraz>"), new TerminalSymbol("D_ZAGRADA",0,new String[0]));
        ruleMap.put(primarniIzrazLZagrada, new PrimarniIzrazLZagrada());

        // <postfiks_izraz> ::= <primarni_izraz>
        SemanticProduction postfiksIzrazPrimarni = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<postfiks_izraz>"), new NonTerminalSymbol("<primarni_izraz>"));
        ruleMap.put(postfiksIzrazPrimarni, new PostfiksIzrazPrimarni());

        //<postfiks_izraz> ::= <postfiks_izraz> L_UGL_ZAGRADA <izraz> D_UGL_ZAGRADA
        SemanticProduction postfiksIzrazNiz = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<postfiks_izraz>"),new NonTerminalSymbol("<postfiks_izraz>"), new TerminalSymbol("L_UGL_ZAGRADA",0,new String[0]),
                new NonTerminalSymbol("<izraz>"), new TerminalSymbol("D_UGL_ZAGRADA",0,new String[0]));
        ruleMap.put(postfiksIzrazNiz, new PostfiksIzrazNiz());

        //<postfiks_izraz> ::= <postfiks_izraz> L_ZAGRADA D_ZAGRADA
        SemanticProduction postfiksIzrazFunction = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<postfiks_izraz>"), new NonTerminalSymbol("<postfiks_izraz>"),new TerminalSymbol("L_ZAGRADA",0, new String[0]),
                new TerminalSymbol("D_ZAGRADA",0, new String[0]));
        ruleMap.put(postfiksIzrazFunction, new PostfiksIzrazFunction());

        //<postfiks_izraz> ::= <postfiks_izraz> L_ZAGRADA <lista_argumenata> D_ZAGRADA
        SemanticProduction postfiksIzrazArg = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<postfiks_izraz>"),new NonTerminalSymbol("<postfiks_izraz>") ,new TerminalSymbol("L_ZAGRADA",0, new String[0]),
                new NonTerminalSymbol("<lista_argumenata>"), new TerminalSymbol("D_ZAGRADA",0, new String[0]));
        ruleMap.put(postfiksIzrazArg, new PostfiksIzrazArg());

        //<postfiks_izraz> ::= <postfiks_izraz> (OP_INC | OP_DEC)
        SemanticProduction postfiksIzrazInc = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<postfiks_izraz>"), new NonTerminalSymbol("<postfiks_izraz>") ,new TerminalSymbol("OP_INC",0,new String[0]));
        SemanticProduction postfiksIzrazDec = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<postfiks_izraz>"), new NonTerminalSymbol("<postfiks_izraz>") ,new TerminalSymbol("OP_DEC",0,new String[0]));
        ruleMap.put(postfiksIzrazInc, new PostfiksIzrazInc());
        ruleMap.put(postfiksIzrazDec, new PostfiksIzrazInc());

        //<lista_argumenata> ::= <izraz_pridruzivanja>
        SemanticProduction listaArgumenataJedan = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<lista_argumenata>"), new NonTerminalSymbol("<izraz_pridruzivanja>"));
        ruleMap.put(listaArgumenataJedan, new ListaArgumenataJedan());

        //<lista_argumenata> ::= <lista_argumenata> ZAREZ <izraz_pridruzivanja>
        SemanticProduction listaArgumenataVise = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<lista_argumenata>"), new NonTerminalSymbol("<lista_argumenata>"), new TerminalSymbol("ZAREZ",0, new String[0]),
                new NonTerminalSymbol("<izraz_pridruzivanja>"));
        ruleMap.put(listaArgumenataVise, new ListaArgumenataVise());

        //<unarni_izraz> ::= <postfiks_izraz>
        SemanticProduction unarniIzrazBez = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<unarni_izraz>"), new NonTerminalSymbol("<postfiks_izraz>"));
        ruleMap.put(unarniIzrazBez, new UnarniIzrazBez());

        //<unarni_izraz> ::= (OP_INC | OP_DEC) <unarni_izraz>
        SemanticProduction unarniIzrazInc = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<unarni_izraz>"), new TerminalSymbol("OP_INC",0, new String[0]), new NonTerminalSymbol("<unarni_izraz>"));
        SemanticProduction unarniIzrazDec = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<unarni_izraz>"), new TerminalSymbol("OP_DEC",0, new String[0]), new NonTerminalSymbol("<unarni_izraz>"));
        ruleMap.put(unarniIzrazInc, new UnarniIzrazInc());
        ruleMap.put(unarniIzrazDec, new UnarniIzrazInc());

        //<unarni_izraz> ::= <unarni_operator> <cast_izraz>
        SemanticProduction unarniIzrazOp = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<unarni_izraz>"), new NonTerminalSymbol("<unarni_operator>"), new NonTerminalSymbol("<cast_izraz>"));
        ruleMap.put(unarniIzrazOp, new UnarniIzrazOp());

        //<cast_izraz> ::= <unarni_izraz>
        SemanticProduction castIzrazBez = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<cast_izraz>"), new NonTerminalSymbol("<unarni_izraz>"));
        ruleMap.put(castIzrazBez, new CastIzrazBez());

        //<cast_izraz> ::= L_ZAGRADA <ime_tipa> D_ZAGRADA <cast_izraz>
        SemanticProduction castIzrazSa = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<cast_izraz>"), new TerminalSymbol("L_ZAGRADA", 0, new String[0]), new NonTerminalSymbol("<ime_tipa>"),
                new TerminalSymbol("D_ZAGRADA",0, new String[0]), new NonTerminalSymbol("<cast_izraz>"));
        ruleMap.put(castIzrazSa, new CastIzrazSa());

        //<ime_tipa> ::= <specifikator_tipa>
        SemanticProduction imeTipaBez = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<ime_tipa>"), new NonTerminalSymbol("<specifikator_tipa>"));
        ruleMap.put(imeTipaBez, new ImeTipaBez());

        //<ime_tipa> ::= KR_CONST <specifikator_tipa>
        SemanticProduction imeTipaConst = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<ime_tipa>"), new TerminalSymbol("KR_CONST", 0, new String[0]), new NonTerminalSymbol("<specifikator_tipa>"));
        ruleMap.put(imeTipaConst, new ImeTipaConst());

        //<specifikator_tipa> ::= KR_VOID
        SemanticProduction specifikatorTipaVoid = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<specifikator_tipa>"), new TerminalSymbol("KR_VOID", 0, new String[0]));
        ruleMap.put(specifikatorTipaVoid, new SpecifikatorTipaVoid());

        //<specifikator_tipa> ::= KR_CHAR
        SemanticProduction specifikatorTipaChar = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<specifikator_tipa>"), new TerminalSymbol("KR_CHAR", 0, new String[0]));
        ruleMap.put(specifikatorTipaChar, new SpecifikatorTipaChar());

        //<specifikator_tipa> ::= KR_INT
        SemanticProduction specifikatorTipaInt = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<specifikator_tipa>"), new TerminalSymbol("KR_INT", 0, new String[0]));
        ruleMap.put(specifikatorTipaInt, new SpecifikatorTipaInt());

        //<multiplikativni_izraz> ::= <cast_izraz>
        SemanticProduction multiplikativniIzrazBez = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<multiplikativni_izraz>"), new NonTerminalSymbol("<cast_izraz>"));
        ruleMap.put(multiplikativniIzrazBez, new MultiplikativniIzrazBez());

        //<multiplikativni_izraz> ::= <multiplikativni_izraz> (OP_PUTA |
        //OP_DIJELI | OP_MOD) <cast_izraz>
        SemanticProduction multiplikativniIzrazPuta = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<multiplikativni_izraz>"),new NonTerminalSymbol("<multiplikativni_izraz>"), new TerminalSymbol("OP_PUTA", 0, new String[0]),new NonTerminalSymbol("<cast_izraz>"));
        SemanticProduction multiplikativniIzrazDijeli = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<multiplikativni_izraz>"),new NonTerminalSymbol("<multiplikativni_izraz>") ,new TerminalSymbol("OP_DIJELI", 0, new String[0]),new NonTerminalSymbol("<cast_izraz>"));
        SemanticProduction multiplikativniIzrazMod = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<multiplikativni_izraz>"),new NonTerminalSymbol("<multiplikativni_izraz>") ,new TerminalSymbol("OP_MOD", 0, new String[0]),new NonTerminalSymbol("<cast_izraz>"));
        ruleMap.put(multiplikativniIzrazPuta, new MultiplikativniIzrazOp());
        ruleMap.put(multiplikativniIzrazDijeli, new MultiplikativniIzrazOp());
        ruleMap.put(multiplikativniIzrazMod, new MultiplikativniIzrazOp());

        //<aditivni_izraz> ::= <multiplikativni_izraz>
        SemanticProduction aditivniIzrazBez = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<aditivni_izraz>"), new NonTerminalSymbol("<multiplikativni_izraz>"));
        ruleMap.put(aditivniIzrazBez, new AditivniIzrazBez());

        //<aditivni_izraz> ::= <aditivni_izraz> (PLUS | MINUS) <multiplikativni_izraz>
        SemanticProduction aditivniIzrazPlus = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<aditivni_izraz>"),new NonTerminalSymbol("<aditivni_izraz>"), new TerminalSymbol("PLUS",0,new String[0]), new NonTerminalSymbol("<multiplikativni_izraz>"));
        SemanticProduction aditivniIzrazMinus = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<aditivni_izraz>"),new NonTerminalSymbol("<aditivni_izraz>"), new TerminalSymbol("MINUS",0,new String[0]), new NonTerminalSymbol("<multiplikativni_izraz>"));
        ruleMap.put(aditivniIzrazPlus, new AditivniIzrazOp());
        ruleMap.put(aditivniIzrazMinus, new AditivniIzrazOp());

        //<odnosni_izraz> ::= <aditivni_izraz>
        SemanticProduction odnosniIzrazBez = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<odnosni_izraz>"), new NonTerminalSymbol("<aditivni_izraz>"));
        ruleMap.put(odnosniIzrazBez, new OdnosniIzrazBez());

        // <odnosni_izraz> ::= <odnosni_izraz> (OP_LT | OP_GT | OP_LTE | OP_GTE)
        //<aditivni_izraz>
        SemanticProduction odnosniIzrazLT = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<odnosni_izraz>"),new NonTerminalSymbol("<odnosni_izraz>"), new TerminalSymbol("OP_LT",0,new String[0]), new NonTerminalSymbol("<aditivni_izraz>"));
        SemanticProduction odnosniIzrazGT = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<odnosni_izraz>"),new NonTerminalSymbol("<odnosni_izraz>"), new TerminalSymbol("OP_GT",0,new String[0]), new NonTerminalSymbol("<aditivni_izraz>"));
        SemanticProduction odnosniIzrazLTE = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<odnosni_izraz>"),new NonTerminalSymbol("<odnosni_izraz>"), new TerminalSymbol("OP_LTE",0,new String[0]), new NonTerminalSymbol("<aditivni_izraz>"));
        SemanticProduction odnosniIzrazGTE = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<odnosni_izraz>"),new NonTerminalSymbol("<odnosni_izraz>"), new TerminalSymbol("OP_GTE",0,new String[0]), new NonTerminalSymbol("<aditivni_izraz>"));
        ruleMap.put(odnosniIzrazLT, new OdnosniIzrazOp());
        ruleMap.put(odnosniIzrazGT, new OdnosniIzrazOp());
        ruleMap.put(odnosniIzrazLTE, new OdnosniIzrazOp());
        ruleMap.put(odnosniIzrazGTE, new OdnosniIzrazOp());

        //<jednakosni_izraz> ::= <odnosni_izraz>
        SemanticProduction jednakosniIzrazBez = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<jednakosni_izraz>"), new NonTerminalSymbol("<odnosni_izraz>"));
        ruleMap.put(jednakosniIzrazBez, new JednakosniIzrazBez());

        //<jednakosni_izraz> ::= <jednakosni_izraz> (OP_EQ | OP_NEQ) <odnosni_izraz>
        SemanticProduction jednakosniIzrazEq = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<jednakosni_izraz>"),new NonTerminalSymbol("<jednakosni_izraz>"), new TerminalSymbol("OP_EQ",0,new String[0]), new NonTerminalSymbol("<odnosni_izraz>"));
        SemanticProduction jednakosniIzrazNeq = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<jednakosni_izraz>"),new NonTerminalSymbol("<jednakosni_izraz>"), new TerminalSymbol("OP_NEQ",0,new String[0]), new NonTerminalSymbol("<odnosni_izraz>"));
        ruleMap.put(jednakosniIzrazEq, new JednakosniIzrazOp());
        ruleMap.put(jednakosniIzrazNeq, new JednakosniIzrazOp());

        //<bin_i_izraz> ::= <jednakosni_izraz>
        SemanticProduction binIIzrazBez = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<bin_i_izraz>"), new NonTerminalSymbol("<jednakosni_izraz>"));
        ruleMap.put(binIIzrazBez, new BinIIzrazBez());

        //<bin_i_izraz> ::= <bin_i_izraz> OP_BIN_I <jednakosni_izraz>
        SemanticProduction binIIzrazOp = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<bin_i_izraz>"),new NonTerminalSymbol("<bin_i_izraz>"), new TerminalSymbol("OP_BIN_I",0, new String[0]), new NonTerminalSymbol("<jednakosni_izraz>"));
        ruleMap.put(binIIzrazOp, new BinIIzrazOp());

        //<bin_xili_izraz> ::= <bin_i_izraz>
        SemanticProduction binXiliIzrazBez = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<bin_xili_izraz>"), new NonTerminalSymbol("<bin_i_izraz>"));
        ruleMap.put(binXiliIzrazBez, new BinXiliIzrazBez());

        //<bin_xili_izraz> ::= <bin_xili_izraz> OP_BIN_XILI <bin_i_izraz>
        SemanticProduction binXiliIzrazOp = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<bin_xili_izraz>"), new NonTerminalSymbol("<bin_xili_izraz>"), new TerminalSymbol("OP_BIN_XILI",0,new String[0]), new NonTerminalSymbol("<bin_i_izraz>"));
        ruleMap.put(binXiliIzrazOp, new BinXiliIzrazOp());

        //<bin_ili_izraz> ::= <bin_xili_izraz>
        SemanticProduction binIliIzrazBez = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<bin_ili_izraz>"), new NonTerminalSymbol("<bin_xili_izraz>"));
        ruleMap.put(binIliIzrazBez, new BinIliIzrazBez());

        //<bin_ili_izraz> ::= <bin_ili_izraz> OP_BIN_ILI <bin_xili_izraz>
        SemanticProduction binIliIzrazOp = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<bin_ili_izraz>"),new NonTerminalSymbol("<bin_ili_izraz>"), new TerminalSymbol("OP_BIN_ILI",0,new String[0]), new NonTerminalSymbol("<bin_xili_izraz>"));
        ruleMap.put(binIliIzrazOp, new BinIliIzrazOp());

        //<log_i_izraz> ::= <bin_ili_izraz>
        SemanticProduction logIIzrazBez = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<log_i_izraz>"), new NonTerminalSymbol("<bin_ili_izraz>"));
        ruleMap.put(logIIzrazBez, new LogIIzrazBez());

        //<log_i_izraz> ::= <log_i_izraz> OP_I <bin_ili_izraz>
        SemanticProduction logIIzrazOp = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<log_i_izraz>"),new NonTerminalSymbol("<log_i_izraz>"), new TerminalSymbol("OP_I",0, new String[0]), new NonTerminalSymbol("<bin_ili_izraz>"));
        ruleMap.put(logIIzrazOp, new LogIIzrazOp());

        //<log_ili_izraz> ::= <log_i_izraz>
        SemanticProduction logIliIzrazBez = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<log_ili_izraz>"), new NonTerminalSymbol("<log_i_izraz>"));
        ruleMap.put(logIliIzrazBez, new LogIliIzrazBez());

        //<log_ili_izraz> ::= <log_ili_izraz> OP_ILI <log_i_izraz>
        SemanticProduction logIliIzrazOp = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<log_ili_izraz>"),new NonTerminalSymbol("<log_ili_izraz>"), new TerminalSymbol("OP_ILI",0, new String[0]), new NonTerminalSymbol("<log_i_izraz>"));
        ruleMap.put(logIliIzrazOp, new LogIliIzrazOp());

        //<izraz_pridruzivanja> ::= <log_ili_izraz>
        SemanticProduction izrazPridruzivanjaBez = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<izraz_pridruzivanja>"), new NonTerminalSymbol("<log_ili_izraz>"));
        ruleMap.put(izrazPridruzivanjaBez, new IzrazPridruzivanjaBez());

        //<izraz_pridruzivanja> ::= <postfiks_izraz> OP_PRIDRUZI <izraz_pridruzivanja>
        SemanticProduction izrazPridruzivanjaOp = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<izraz_pridruzivanja>"), new NonTerminalSymbol("<postfiks_izraz>"), new TerminalSymbol("OP_PRIDRUZI",0,new String[0]), new NonTerminalSymbol("<izraz_pridruzivanja>"));
        ruleMap.put(izrazPridruzivanjaOp, new IzrazPridruzivanjaOp());

        //<izraz> ::= <izraz_pridruzivanja>
        SemanticProduction izrazJedan = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<izraz>"), new NonTerminalSymbol("<izraz_pridruzivanja>"));
        ruleMap.put(izrazJedan, new IzrazJedan());

        //<izraz> ::= <izraz> ZAREZ <izraz_pridruzivanja>
        SemanticProduction izrazVise = SemanticProduction.generateMapKeyProduction(new NonTerminalSymbol("<izraz>"),new NonTerminalSymbol("<izraz>"), new TerminalSymbol("ZAREZ",0, new String[0]), new NonTerminalSymbol("<izraz_pridruzivanja>"));
        ruleMap.put(izrazVise, new IzrazVise());

        //---------------------------------------------------------------------------------------------


    }

    public Map<SemanticProduction, Object> getRuleMap() {
        return ruleMap;
    }

    public static boolean implicitCast(String typeBefore, String typeAfter) {
        if(typeBefore.equals(typeAfter))
            return true;
        else if(typeAfter.contains(typeBefore))
            return true;
        else if(typeBefore.length() > 4 && typeAfter.equals(typeBefore.substring(6,typeBefore.length()-1)))
            return true;
        else if(typeBefore.equals("char") && typeAfter.equals("int"))
            return true;
        else if((typeBefore.contains("niz(") && !typeBefore.contains("const") ) && typeAfter.contains("niz(const("))
            return true;
        else {
            String[] types = new String[] {"int","char","const(int)","const(char)","niz(int)", "niz(char)", "niz(const(int))", "niz(const(char))"};
            for(String type : types) {
                if(implicitCast(typeBefore,type) && implicitCast(type,typeAfter))
                    return true;
            }
            return false;
        }
    }

    public static boolean explicitCast(String typeBefore, String typeAfter) {
        if(typeBefore.equals("int") && typeAfter.equals("char"))
            return true;
        else return typeBefore.equals("int") || typeBefore.equals("char");
    }
}
