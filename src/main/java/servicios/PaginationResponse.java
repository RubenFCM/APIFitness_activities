package servicios;

import com.google.gson.annotations.Expose;

import java.util.List;

public class PaginationResponse<T> {
    @Expose
    private List<T> elements;

    @Expose
    private long totalElements;

    @Expose
    private int pageNumber;

    @Expose
    private int pageSize;

    public PaginationResponse() {}

    public PaginationResponse(List<T> elements, long totalElements, int pageNumber, int pageSize) {
        this.elements = elements;
        this.totalElements = totalElements;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
