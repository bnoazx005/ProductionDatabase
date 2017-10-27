**Title**:        ProductionDatabase

**Author**:       Kasimov Ildar

**e-mail**:       ildar2571@yandex.ru

**Project's directories structure:**

- __./UML/__ - the folder contains UML diagram that was created in NClass diagram editor (http://nclass.sourceforge.net/). There is also a diagram in *.png format.

**The database's description**

<Production>
A minimal list of characteristics:

- product: code of a product, its name, is it a standard, notes, goals of its production,production amount per year;

- company (manufacturer): its code and name, phone and address;

- material: name, type, units, price per unit, notes about using in some product;

- specification: approval and date, a list of materials and amount per material, year when a product was produced;

A list of queries that should be implemented:

- Display a product that contains the most materials of "non-ferrous metal" type.

- Display a list of products for which the cost of materials in Y year decreased compared to the previous year.

- Display a list of products that were not produced in Yth year.

- Display an average consumption of Nth material in Yth year.

- Display the youngest and the oldest products.

- Display a product that consists of more amount of materials than others.

- Display the most used material.

- Display an information about a company that has lowest amount of products.

- Display the most expensive product.
