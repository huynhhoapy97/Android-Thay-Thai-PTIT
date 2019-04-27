package truonghuynhhoa.ptit.model;

public class MonHoc {
    private String ma;
    private String ten;
    private Integer soTiet;

    public MonHoc() {
    }

    public MonHoc(String ma, String ten, Integer soTiet) {
        this.ma = ma;
        this.ten = ten;
        this.soTiet = soTiet;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Integer getSoTiet() {
        return soTiet;
    }

    public void setSoTiet(Integer soTiet) {
        this.soTiet = soTiet;
    }
}
