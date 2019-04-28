package truonghuynhhoa.ptit.model;

public class ChiTietHoaDon {
    private Integer soHoaDon;
    private String maHang;
    private Integer soLuong;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(Integer soHoaDon, String maHang, Integer soLuong) {
        this.soHoaDon = soHoaDon;
        this.maHang = maHang;
        this.soLuong = soLuong;
    }

    public Integer getSoHoaDon() {
        return soHoaDon;
    }

    public void setSoHoaDon(Integer soHoaDon) {
        this.soHoaDon = soHoaDon;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
}
