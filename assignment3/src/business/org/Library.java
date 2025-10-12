package business.org;

public class Library {
    private int buildingNo;
    public Library(int buildingNo) { this.buildingNo = buildingNo; }
    public int getBuildingNo() { return buildingNo; }
    public void setBuildingNo(int n) { this.buildingNo = n; }
    @Override public String toString() { return "Library #" + buildingNo; }
}
