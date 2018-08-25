package scicalc;

class Symbol implements Token
{
    enum SymbolType {PARENTHESIS_RIGHT, PARENTHESIS_LEFT};
    SymbolType symbolType;
    String representation;
    
    Symbol (SymbolType symbolType)
    {
        this.symbolType=symbolType;
        if (symbolType==SymbolType.PARENTHESIS_LEFT)
        {
            representation="(";
        }
        else if (symbolType==SymbolType.PARENTHESIS_RIGHT)
        {
            representation=")";
        }
    }
    
    public String toString ()
    {
        return representation;
    }
}
