package truonghuynhhoa.ptit.model;

public class KhachHang {
    private Integer ma;
    private String ten;
    private String diaChi;
    private String dienThoai;

    public KhachHang() {
    }

    public KhachHang(Integer ma, String ten, String diaChi, String dienThoai) {
        this.ma = ma;
        this.ten = ten;
        this.diaChi = diaChi;
        this.dienThoai = dienThoai;
    }

    public Integer getMa() {
        return ma;
    }

    public void setMa(Integer ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }
}
