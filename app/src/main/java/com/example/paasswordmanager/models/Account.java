package com.example.paasswordmanager.models;

public class Account {
    // Mandatory fields
    private String siteName;
    private String email;
    private String password;
    private String registerDate;

    // Optional fields
    private String username;
    private String phone;
    private String secretQuestion;
    private String passwordHint;
    private String altEmail;
    private String externalAccess; // OAuth
    private String notes;
    private String category;
    private String usage;
    private String url;

    public Account(String siteName, String email, String password, String registerDate) {
        this.siteName = siteName;
        this.email = email;
        this.password = password;
        this.registerDate = registerDate;
    }

    // Getters and Setters
    public String getSiteName() { return siteName; }
    public void setSiteName(String siteName) { this.siteName = siteName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRegisterDate() { return registerDate; }
    public void setRegisterDate(String registerDate) { this.registerDate = registerDate; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getSecretQuestion() { return secretQuestion; }
    public void setSecretQuestion(String secretQuestion) { this.secretQuestion = secretQuestion; }
    public String getPasswordHint() { return passwordHint; }
    public void setPasswordHint(String passwordHint) { this.passwordHint = passwordHint; }
    public String getAltEmail() { return altEmail; }
    public void setAltEmail(String altEmail) { this.altEmail = altEmail; }
    public String getExternalAccess() { return externalAccess; }
    public void setExternalAccess(String externalAccess) { this.externalAccess = externalAccess; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getUsage() { return usage; }
    public void setUsage(String usage) { this.usage = usage; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    // Backward compatibility or convenience getters
    public String getName() { return siteName; }
}
