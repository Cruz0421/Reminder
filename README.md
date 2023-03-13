# Reminder

## What is the Remider App
```
Reminder application for CMPS 3390 Application Development 
```
## Key Features
```
  -Sends notifications
  -Set timer by time length
  -Repeat
  -Checklist
  -Mark when tasks are done
  -Remind again later
  -Categorical sorting
  -Sort reminders/checklists by group
  -Communicates with database 
  -User sign in
  -Data backup
  -Hosted on Odin
```
## Team
```
  -Patrick Cruz
  -Taylor Hooser
```
## Compile Fixes
```
ERROR: Installed Build Tools revision 33.0.2 is corrupted
FIX: Android/Sdk/build-tools/33.0.2/
    rename d8.bat to dx.bat
    rename lib/d8.jar to lib/dx.jar

cd ~/student/Android/sdk/build-tools/33.0.2 \
  && mv d8 dx \
  && cd lib  \
  && mv d8.jar dx.jar

ERROR: resource xml/data_extraction_rules not found
FIX: remove following code in AndroidManifest.xml:
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
```
