package truonghuynhhoa.ptit.model;

public class MatHangTrongHoaDon {
    public String ma;
    public String ten;
    public String donVi;
    public String donGia;
    public Integer soLuong;

    public MatHangTrongHoaDon() {
    }

    public MatHangTrongHoaDon(String ma, String ten, String donVi, String donGia, Integer soLuong) {
        this.ma = ma;
        this.ten = ten;
        this.donVi = donVi;
        this.donGia = donGia;
        this.soLuong = soLuong;
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

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public String getDonGia() {
        return donGia;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
}
