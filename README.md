# Glossary

Write a Glossary locally or share your work with anyone, anywhere, on any desktop computer.

Enrico Fasoli, Gianluca Rocco, Roberto Dalipaj, Marco Carne, Raffaele Cristodaro, Sara Dendena

## How it works

The server and the client each have a Glossary and they keep it in sync using our network protocol. The client has a built in "ad-hoc" server that can be used to quickly listen to connection from other clients.
The headless Server can also operate using a MySQL database instead of a regular text file.

_Record_: an entry in the glossary is composed of a term and the meaning of the term.

_Thread safety_: all glossary operations are thread safe using java's built in critical section keyword "synchronized".

_Protocol_: over the network, messages are sent in the form of strings with commands. There are two commands: a glossary upsert operation and a delete operation. Commands can only contain a single operation (so multiple commands are sent for multiple operations).

_Interface_:
- list of terms: the list of terms in the current glossary
- filter: used to search for a term or multiple terms
- buttons: add, delete or update terms.
- menus:
    - change network settings
    - import or export a glossary (using files)
    - host an ad-hoc server
    - connect to a server

## Implementation

As stated before, everything is clear and commented in the code so you're free to look    around and understand how it was implemented. We suggest starting from the GlossaryLib,   then the server and finally the client.

### Importing the project

- You need Netbeans 8.0 or higher.
- Import all three project _at once_ and verify that the library links are correct.
- Make sure the __working directory__ option in _Project Properties -> Run_ is set to "src" or some files will not be found.

## Workforce Organization

The project was organized using _Agile development_: every member of the group understands the "bigger picture" and although the work was initially split, it was more of a guideline then strict rules.

This has a number of advantages:
- _Equality_: every project member understands the bigger picture and can explain the whole program.
- _Shared knowledge_: thanks to the previous point (everyone must understand everything) all the code must be clear and commented.
- _Better collaboration_: a project member can fix, improve or change another member's work because thanks to Version Control (git) one can always revert any change operation and nothing is lost.
- _Freedom_: every project member is free to improve or add something if he wants to or he has this big idea without creating problems.
