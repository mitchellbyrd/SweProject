create table Foods (
  Id int identity(1,1) primary key
, Name varchar(200) not null unique
, LastConsumed varchar(32) );

create table ServingSizes (
  Id int identity(1,1) primary key
, ServingSizeType varchar(85) not null
, Amount float not null
, Calories float not null
, Proteins float not null
, Carbs float not null
, Fats float not null
, FoodId int foreign key references Foods(Id) );

create table Users (
  Id int identity(1,1) primary key
, EmailAddress varchar not null unique
, Password varchar not null unique
, NameFirst varchar not null
, NameLast varchar
, BirthDate varchar
, Weight float
, PreferedCalorieLimit float 
, PreferedFatLimit float
, PreferedCarbLimit float
, PreferedProteinLimit float );

create table Days (
  Id int identity(1,1) primary key
, UserId int foreign key references Users(Id)
, Date datetime not null
, CurrentTotalCalorie float not null
, CurrentTotalFat float not null
, CurrentTotalCarb float not null
, CurrentTotalProtein float not null
, GoalTotalCalorie float not null
, GoalTotalFat float not null
, GoalTotalCarb float not null
, GoalTotalProtein float );

create table Food_Day (
  Id int identity(1,1) primary key
, ServingSizesId int foreign key references ServingSizes(Id)
, DayId int foreign key references Days(Id)
, NumberOfServings float not null );