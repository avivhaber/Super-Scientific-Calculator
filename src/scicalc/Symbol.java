package scicalc;

class Symbol
{
    enum SymbolType {PARENTHESIS_OPEN, PARENTHESIS_CLOSE};
    private SymbolType symbolType;
    private String representation;
    
    Symbol (SymbolType symbolType)
    {
        this.symbolType=symbolType;
        if (symbolType==SymbolType.PARENTHESIS_OPEN)
        {
            representation="(";
        }
        else if (symbolType==SymbolType.PARENTHESIS_CLOSE)
        {
            representation=")";
        }
    }
    
    SymbolType getSymbolType ()
    {
        return this.symbolType;
    }
    
    public String toString()
    {
        return representation;
    }
}
