# api_lbs
API pour le bon sandwich

Lucas Marquant 

Nicolas Jacquemin

LP Cisiie Gr. 2 


Route :

-ipServeur:-port/api_lbs/api/categories ---------------> Affiche toute les catégories

-ipServeur:-port/api_lbs/api/categories/{id} ----------> Affiche la catégorie de l'id correspondant a la route

-ipServeur:-port/api_lbs/api/sandwichs ----------------> Affiche toute les sandwichs

-ipServeur:-port/api_lbs/api/sandwichs/{id} -----------> Affiche le sandwichs de l'id correspondant a la route

-ipServeur:-port/api_lbs/api/sandwichs?t={type_pain} --> Affiche toute les sandwichs dont le type de pain corresponds au parametre t

-ipServeur:-port/api_lbs/api/sandwichs?img={img} --> Affiche toute les sandwichs dont l'url de l'image corresponds au parametre img

-ipServeur:-port/api_lbs/api/sandwichs?img={img}&t={type_pain} --> Affiche toute les sandwichs dont l'url de l'image corresponds au parametre img et dont le type de pain corresponds au parametre t
