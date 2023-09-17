package views;

public enum FormaPago {
	
    EFECTIVO("EFECTIVO"),
    TARJETA("TARJETA"),
    TRANSFERENCIA("TRANSFERENCIA");

    private final String label;

    private FormaPago(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

