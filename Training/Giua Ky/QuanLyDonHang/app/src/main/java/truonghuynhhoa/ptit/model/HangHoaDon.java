package truonghuynhhoa.ptit.model;

public class HangHoaDon {
    private int soThuTu;
    private String maHang;
    private String tenHang;
    private int soLuong;

    public HangHoaDon() {
    }

    public HangHoaDon(int soThuTu, String maHang, String tenHang, int soLuong) {
        this.soThuTu = soThuTu;
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.soLuong = soLuong;
    }

    public int getSoThuTu() {
        return soThuTu;
    }

    public void setSoThuTu(int soThuTu) {
        this.soThuTu = soThuTu;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
