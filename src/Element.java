public enum Element {
    FIRE, AQUA, PLANT, ELECTRIC;

    public static int compare(Element atkElem, Element victElem){
        if(atkElem.equals(AQUA) && victElem.equals(PLANT) || atkElem.equals(ELECTRIC) && victElem.equals(PLANT) || atkElem.equals(FIRE) && victElem.equals(AQUA) || atkElem.equals(victElem)) {
            return -1;
        }
        if(atkElem.equals(PLANT) && victElem.equals(AQUA) || atkElem.equals(ELECTRIC) && victElem.equals(AQUA) || atkElem.equals(FIRE) && victElem.equals(PLANT) || atkElem.equals(AQUA) && victElem.equals(FIRE)){
            return 1;
        }
        return 0;
    }

}
