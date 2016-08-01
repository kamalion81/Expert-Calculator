package profiles;

/**
 *
 * @author Kamalion
 */
public class SteelProfile {
    
    private int id;
    private String mark;
    private int sigma20;
    private int sigmaT;

    public SteelProfile(int id, String mark, int sigma20, int sigmaT) {
        this.id = id;
        this.mark = mark;
        this.sigma20 = sigma20;
        this.sigmaT = sigmaT;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the mark
     */
    public String getMark() {
        return mark;
    }

    /**
     * @param mark the mark to set
     */
    public void setMark(String mark) {
        this.mark = mark;
    }

    /**
     * @return the sigma20
     */
    public int getSigma20() {
        return sigma20;
    }

    /**
     * @param sigma20 the sigma20 to set
     */
    public void setSigma20(int sigma20) {
        this.sigma20 = sigma20;
    }

    /**
     * @return the sigmaT
     */
    public int getSigmaT() {
        return sigmaT;
    }

    /**
     * @param sigmaT the sigmaT to set
     */
    public void setSigmaT(int sigmaT) {
        this.sigmaT = sigmaT;
    }
    
    
    
}
