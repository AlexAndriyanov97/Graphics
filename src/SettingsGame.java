public class SettingsGame {

    private int widthOfMap;
    private int heightOfMap;
    private int sizeOfHex;
    private int thicknessOfLine;

    private double lifeBegin;
    private double lifeEnd;
    private double birthBegin;
    private double birthEnd;
    private double firstImpact;
    private double secondImpact;


    public SettingsGame(int widthOfMap, int heightOfMap, int sizeOfHex, int thicknessOfLine) {
        this.widthOfMap = widthOfMap;
        this.heightOfMap = heightOfMap;
        this.sizeOfHex = sizeOfHex;
        this.thicknessOfLine = thicknessOfLine;
    }

    public SettingsGame() {
        lifeBegin = 2.0;
        lifeEnd = 3.3;
        birthBegin = 2.3;
        birthEnd = 2.9;
        firstImpact = 1.0;
        secondImpact = 0.3;
    }


    public void setWidthOfMap(int widthOfMap) {
        this.widthOfMap = widthOfMap;
    }

    public void setHeightOfMap(int heightOfMap) {
        this.heightOfMap = heightOfMap;
    }

    public void setSizeOfHex(int sizeOfHex) {
        this.sizeOfHex = sizeOfHex;
    }

    public void setThicknessOfLine(int thicknessOfLine) {
        this.thicknessOfLine = thicknessOfLine;
    }

    public void setLifeBegin(double lifeBegin) {
        this.lifeBegin = lifeBegin;
    }

    public void setLifeEnd(double lifeEnd) {
        this.lifeEnd = lifeEnd;
    }

    public void setBirthBegin(double birthBegin) {
        this.birthBegin = birthBegin;
    }

    public void setBirthEnd(double birthEnd) {
        this.birthEnd = birthEnd;
    }

    public void setFirstImpact(double firstImpact) {
        this.firstImpact = firstImpact;
    }

    public void setSecondImpact(double secondImpact) {
        this.secondImpact = secondImpact;
    }

    public int getWidthOfMap() {
        return widthOfMap;
    }

    public int getHeightOfMap() {
        return heightOfMap;
    }

    public int getSizeOfHex() {
        return sizeOfHex;
    }

    public int getThicknessOfLine() {
        return thicknessOfLine;
    }

    public double getLifeBegin() {
        return lifeBegin;
    }

    public double getLifeEnd() {
        return lifeEnd;
    }

    public double getBirthBegin() {
        return birthBegin;
    }

    public double getBirthEnd() {
        return birthEnd;
    }

    public double getFirstImpact() {
        return firstImpact;
    }

    public double getSecondImpact() {
        return secondImpact;
    }
}
