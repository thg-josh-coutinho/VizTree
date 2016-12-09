Project Description:
====================
Visualization flow through the order manager pipeline


Build Instr:
============

Download and install all of the depedencies. Then `cd` into the `$project_home/prototype`, `mvn install`.

Running Instr:
==============

After building the project run `mvn exec:java`


Dependencies:
============

jgraph library, available at:

java socket io library available at:


Adding a New Event:
===================

In the camel routes section add the statement to multicast to the queue:
flowvisualization.om


This currently also needs to be added to AppTest in the if case block under the deserializeEvent section.


Testing:
========
Stable activemq: activemq1.st.thehutgroup.local:8161/admin/queues.jsp
Live activemq: gblmsg005.thehut.local:8161/admin/queues.jsp
