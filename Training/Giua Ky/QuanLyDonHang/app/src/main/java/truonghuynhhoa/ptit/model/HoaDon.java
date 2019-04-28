package truonghuynhhoa.ptit.model;

import java.util.Date;

public class HoaDon {
    private Integer soHoaDon;
    private String ngayLap;
    private String ngayGiao;
    private Integer maKhachHang;

    public HoaDon() {
    }

    public HoaDon(Integer soHoaDon, String ngayLap, String ngayGiao, Integer maKhachHang) {
        this.soHoaDon = soHoaDon;
        this.ngayLap = ngayLap;
        this.ngayGiao = ngayGiao;
        this.maKhachHang = maKhachHang;
    }

    public Integer getSoHoaDon() {
        return soHoaDon;
    }

    public void setSoHoaDon(Integer soHoaDon) {
        this.soHoaDon = soHoaDon;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getNgayGiao() {
        return ngayGiao;
    }

    public void setNgayGiao(String ngayGiao) {
        this.ngayGiao = ngayGiao;
    }

    public Integer getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(Integer maKhachHang) {
        this.maKhachHang = maKhachHang;
    }
}
