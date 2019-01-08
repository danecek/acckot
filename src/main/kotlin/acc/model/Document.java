package acc.model;

import java.time.LocalDate;

public class Document {

    private final DocumentId id;
    private final DocumentType type;
    private final String name;
   // private final Optional<AnalAcc> optAccount;
    private final LocalDate date;
    private final String description;

    public Document(DocumentId id, DocumentType type, String name, LocalDate date,
  //          Optional<AnalAcc> optAccount, 
            String description) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.date = date;
        //      this.optCompany = optCompany;
    //    this.optAccount = optAccount;
        this.description = description;
    }

    /**
     * @return the id
     */
    public DocumentId getId() {
        return id;
    }

    /**
     * @return the type
     */
    public DocumentType getType() {
        return type;
    }

    public String getTypeText() {
        return getType().getText();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

//    /**
//     * @return the optAccount
//     */
//    public Optional<AnalAcc> getOptAccount() {
//        return optAccount;
//    }

//    public String getAccountNumber() {
//        return optAccount.isPresent() ? optAccount.get().getNumber() : "";
//    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

}
