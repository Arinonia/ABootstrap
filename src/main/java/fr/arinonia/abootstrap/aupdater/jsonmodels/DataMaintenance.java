package fr.arinonia.abootstrap.aupdater.jsonmodels;

public class DataMaintenance {

    private boolean maintenance;
    private String message;

    /**
     *
     * @return the status of updater (actually in maintenance or not)
     */
    public boolean isMaintenance() {
        return this.maintenance;
    }

    /**
     *
     * @return the message of the maintenance if she enables
     */
    public String getMessage() {
        return this.message;
    }

    /**
     *
     * @param maintenance value of maintenance on the JSON
     */
    public void setMaintenance(final boolean maintenance) {
        this.maintenance = maintenance;
    }

    /**
     *
     * @param message reason of maintenance
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
