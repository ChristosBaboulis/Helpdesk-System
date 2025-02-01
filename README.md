# Helpdesk System

To σύστημα παρακολούθησης αιτημάτων υποστήριξης στοχεύει στη
βελτιστοποίηση της διαχείρισης τεχνικών αιτημάτων για εταιρείες τηλεπικοινωνιών.

Οι χρήστες-πελάτες θα μπορούν να υποβάλλουν αιτήματα, να παρακολουθούν την κατάσταση
επίλυσής τους και να λαμβάνουν ενημερώσεις μέσω REST APIs. Το σύστημα θα αποθηκεύει
τις πληροφορίες σε σχεσιακή βάση δεδομένων, παρέχοντας αναφορές σχετικά με τον μέσο
χρόνο επίλυσης και τον φόρτο εργασίας.

Πιο συγκεκριμένα:
1. H υπηρεσία υποστηρίζει την καταγραφή, ανάθεση και παρακολούθηση αιτημάτων τεχνικής υποστήριξης από πελάτες μιας εταιρείας τηλεπικοινωνιών.
2. H καταγραφή των αιτημάτων γίνεται από το προσωπικό εξυπηρέτησης πελάτων, μετά από τηλεφωνική επικοινωνία του πελάτη που έχει ανάγκη υποστήριξης.
3. Για κάθε αίτημα καταγράφονται τα στοιχεία του πελάτη, ο αριθμός της τηλεφωνικής γραμμής που έχει πρόβλημα, καθώς και η περιγραφή του προβλήματος.
4. Τα αιτήματα υπάγονται σε διάφορες κατηγορίες όπως τιμολόγηση, συνδεσιμότητα, τηλεφωνική υπηρεσία, ταχύτητα σύνδεσης κτλ.
5. το σύστημα αναθέτει αυτόματα τα αιτήματα υποστήριξης σε κατάλληλους μηχανικούς ανάλογα με την κατηγορία του αιτήματος και το πλήθος των αιτημάτων που εκκρεμούν προς διεκπεραίωση από τον μηχανικό, με στόχο την ισοκατανομή του φορτίου αιτημάτων μεταξύ των μηχανικών.
6. Κάθε μηχανικός μπορεί να χειριστεί συγκεκριμένες κατηγορίες αιτημάτων ανάλογα με την ειδικότητά του.
7. Η ομάδα διαχείρισης της εφαρμογής συνδέει ειδικότητες μηχανικών με κατηγορίες αιτημάτων και αναθέτει μια ειδικότητα σε κάθε μηχανικό μετά από συνεννόηση με το τμήμα προσωπικού.
8. Κατά την επίλυση ενός αιτήματος ο μηχανικός καταγράφει τις ενέργειες που έχει εκτελέσει, οι οποίες υπάγονται σε δυο κατηγορίες: (α) τεχνικές εργασίες και (β) επικοινωνίες με τον πελάτη.
9. Για κάθε ενέργεια αναφέρεται ο τίτλος, η περιγραφή και η ημερομηνία εκτέλεσής.
10. Ειδικά για την επικοινωνία με τον πελάτη καταγράφεται και η διάρκεια της κλήσης.
11. Με την ολοκλήρωση της επίλυσης/διευθέτησης του αιτήματος, το σύστημα ειδοποιεί τον πελάτη μέσω email.
12. Το σύστημα παράγει στατιστικά στοιχεία προς τη διοίκηση, όπως διάρκεια επίλυσης αιτημάτων ανά κατηγορία αιτημάτων, μέσο πλήθος επικοινωνιών με τον πελάτη ανά κατηγορία αιτήματος, πλήθος αιτημάτων ανά μήνα κτλ.



Η συγκεκριμένη έκδοση έχει αναπτυχθεί με το framework [Quarkus](https://quarkus.io/).

Αξιοποιεί το JPA 2 και το [Hibernate](https://hibernate.org/orm/) ως JPA Provider για υποστήριξη πρόσβασης στη βάση δεδομένων.
Παρέχει ως REST υπηρεσίες με αξιοποίηση του JAX-RS API.

Οικοδόμηση
----------
Για την οικοδόμηση απαιτείται Java 21.

Η οικοδόμηση (build) του λογισμικού γίνεται με το εργαλείο [Maven 3](https://maven.apache.org/)
Η εγκατάσταση του Maven είναι σχετικά απλή. Αφού κατεβάσουμε το Maven (π.χ. έκδοση 3.9.9) και τα αποσυμπιέσουμε σε κατάλληλους καταλόγους
π.χ. <code> C:\\Program Files\\Apache\\Maven\\apache-maven-3.9.9\\ </code> αντίστοιχα θα πρέπει:

* να ορίσουμε τη μεταβλητή περιβάλλοντος JAVA_HOME η οποία θα δείχνει στον κατάλογο εγκατάστασης του JDK,
* να προσθέσουμε τον κατάλογο <code> C:\\Program Files\\Apache\\Maven\\apache-maven-3.9.9\\bin </code> στη μεταβλητή περιβάλλοντος PATH.
* να ορίσουμε τη μεταβλητή περιβάλλοντος MAVEN_HOME. Στο παράδειγμά μας είναι ο κατάλογος <code>C:\\Program Files\\Apache\\Maven\\apache-maven-3.9.9\\</code>.

Για να εκτελέσουμε από τη γραμμή εντολών εργασίες οικοδόμησης του λογισμικού χρησιμοποιούμε το Maven μέσω της εντολής <code>mvn</code>,
αφού μετακινηθούμε στον κατάλογο όπου βρίσκεται το αρχείο pom.xml. Η τυπική εκτέλεση του Maven είναι:

<code>mvn [options] [target [target2 [target3] … ]]</code>

Τα παραγόμενα αρχεία δημιουργούνται από το Maven στο κατάλογο <code>/target</code>.

Τυπικές εργασίες με το Maven είναι:
* <code>mvn clean</code> καθαρισμός του project. Διαγράφονται όλα τα αρχεία του καταλόγου <code>/target</code>.
* <code>mvn compile</code> μεταγλώττιση του πηγαίου κώδικα. Τα αρχεία <code>.class</code> παράγονται στον κατάλογο <code>/target/classes</code>.
* <code>mvn test-compile</code> μεταγλώττιση του κώδικα ελέγχου. Τα αρχεία .class παράγονται στον κατάλογο <code>/target/test-classes</code>.
* <code>mvn test</code> εκτέλεση των ελέγχων με το JUnit framework.
* <code>mvn site</code> παραγωγή στο site του έργου το οποίο περιλαμβάνει την τεκμηρίωση του έργου.
* <code>mvn umlet:convert -Dumlet.targetDir=src/site/markdown/uml</code> παράγει αρχεία εικόνας png για όλα τα διαγράμματα που βρίσκονται στην τοποθεσία `src/site/markdown/uml`. Συστήνεται η κλήση της εντολής πριν την υποβολή μιας νέας έκδοσης διαγραμμάτων στο git repository (`git commit`). Ως αποτέλεσμα τα παραγόμενα αρχεία εικόνας των διαγραμμάτων συνοδεύουν τα πηγαία αρχεία έτσι ώστε να είναι εύκολη η πλοήγηση στην τεκμηρίωση του project  μέσω του github.

Τεκμηρίωση
----------

Για την τεκμηρίωση του λογισμικού χρησιμοποιήθηκε το Markdown markup για τη συγγραφή των κειμένων και το εργαλείο UMLet για την κατασκευή των διαγραμμάτων UML.

Εντός του φακέλου docs βρίσκονται φάκελοι με τα κατάλληλα αρχεία διαγραμμάτων, φωτογραφιών, markdown αρχείων.

Ακολουθεί σύνδεσμος για το διάγραμμα μοντέλου πεδίου.

[Διάγραμμα Μοντέλου Πεδίου](docs/MD_Files/Domain_Model.md)