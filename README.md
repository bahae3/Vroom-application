# Vroom Vroom

**Vroom Vroom** est une application de covoiturage full-stack (mobile & web) permettant aux utilisateurs de proposer ou de réserver des trajets, de gérer les paiements, de recevoir des notifications en temps réel et d’administrer le service via un back-office.
**PS:** Le code sur ce repository est juste le code-source backend de l'application. Le code-source mobile est sur: https://github.com/bahae3/covoiturage-mobile
---

## 🚀 Technologies

- **Mobile** : React Native (Expo)  
- **Web Admin** : React.js  
- **Backend** : Java, Spring Boot, Spring Security (JWT)  
- **Base de données** : MySQL (schéma disponible dans `vroom.sql`)  
- **Accès aux données** : JDBC  

---

## ⚙️ Prérequis

- Java 17+ & Maven  
- Node.js 14+ & npm ou yarn  
- Expo CLI (`npm install –g expo-cli`)  
- MySQL Server (>= 5.7)

---

## 📦 Installation et lancement

1. **Cloner le dépôt**  
   ```bash
   git clone https://github.com/bahae3/Vroom-application.git
   cd Vroom-application
   ```
   
2. **Préparer la base de données**
Créer une base MySQL nommée vroom_db.
Importer le schéma et les données initiales :
```bash
mysql -u root -p vroom_db < vroom.sql
```

3. **Lancer le backend**
```bash
cd src
mvn clean spring-boot:run
```
L’API REST sera disponible sur http://localhost:8080/api.

4. **Lancer l’interface web d’administration**

```bash
cd ../admin
npm install
npm start
```
Accessible sur http://localhost:3000

5. **Lancer l’application mobile**

```bash
cd ../mobile
npm install
expo start
```
Scanner le QR code avec l’app Expo Go ou émulateur Android/iOS.
