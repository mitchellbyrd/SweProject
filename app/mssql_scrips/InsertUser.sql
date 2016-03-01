if objectproperty(object_id('dbo.InsertUser'), N'IsProcedure') = 1
	drop procedure dbo.InsertUser
go

create procedure dbo.InsertUser
	@emailAddress varchar(64)
,	@password varchar(64)
,	@nameFirst varchar(32)
,	@nameLast varchar(32)
,	@birthDate datetime
,	@weight float
,	@PreferedCalorieLimit float
,	@PreferedCarbLimit float
,	@PreferedFatLimit float
,	@PreferedProteinLimit float
as 
	set nocount on

	insert into dbo.Users(
		EmailAddress
	,	Password
	,	NameFirst
	,	NameLast
	,	Birthdate
	,	Weight
	,	PreferedCalorieLimit
	,	PreferedFatLimit
	,	PreferedCarbLimit
	,	PreferedProteinLimit)
	values (
		@emailAddress
	,	@password
	,	@nameFirst
	,	@nameLast
	,	@birthDate
	,	@weight
	,	@PreferedCalorieLimit
	,	@PreferedFatLimit
	,	@PreferedCarbLimit
	,	@PreferedProteinLimit )

	select
		u.Id
	from
		dbo.Users u
	where
		u.EmailAddress = @emailAddress
		and u.password = @password
go