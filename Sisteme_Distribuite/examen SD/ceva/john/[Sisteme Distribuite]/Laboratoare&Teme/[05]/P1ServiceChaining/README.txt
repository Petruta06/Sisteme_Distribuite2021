Rezumat

Programul initial continea 3 servicii (PrimeNumberGenerator, UnionOperation, CartezianProductOperation), care erau orchestrate de catre Controller.

Modificarea facuta consta in adaugarea unui serviciu SetGenerator si incercarea de eliminare a acestei orchestrari. Controller-ul va orchestra doar doua servicii, care vor fi inlantuite de alte 2 servicii, astfel:
	- chain 1 :  SetGenerator -> PrimeNumberGenerator
	- chain 2 :  UnionOperation -> CartezianProductOperation


*** Observatie ***
	
Nu s-ar fi putut elimina complet orchestrarea, intrucat interfata presupune executarea a doua operatii executate de doua servicii diferite, operatia de generare de multimi si operatia de realizare a operatiei finale (produsul cartezian). Asadar, m-am concentrat de pastrarea acestei functionalitati prin pastrarea serviciilor si realizarea inlantuirii celorlalte servicii. 

