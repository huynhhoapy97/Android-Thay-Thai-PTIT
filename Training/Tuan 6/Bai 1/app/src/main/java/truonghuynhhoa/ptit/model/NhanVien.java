package truonghuynhhoa.ptit.model;

public class NhanVien {
    private String ma;
    private String ten;
    private Boolean gioiTinh;

    public NhanVien() {
    }

    public NhanVien(String ma, String ten, Boolean gioiTinh) {
        this.ma = ma;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
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

    public Boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
}
