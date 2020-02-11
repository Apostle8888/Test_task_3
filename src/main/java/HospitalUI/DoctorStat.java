package HospitalUI;

import java.io.Serializable;

public class DoctorStat implements Serializable, Cloneable
{
    private String statistic;

    public DoctorStat() {}

    public String getStatistic() {
        return statistic;
    }
    public void setStatistic(String statistic) {
        this.statistic = statistic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoctorStat that = (DoctorStat) o;

        return statistic != null ? statistic.equals(that.statistic) : that.statistic == null;
    }

    @Override
    public int hashCode() {
        return statistic != null ? statistic.hashCode() : 0;
    }

    @Override
    public String toString() {
        return statistic;
    }

    @Override
    public DoctorStat clone() throws CloneNotSupportedException {
        return (DoctorStat) super.clone();
    }

}
