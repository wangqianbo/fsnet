$(function() {

	// init
	displayExpNone();
	displayDegreeNone();
	displayTrainingNone();
	displayHobbieNone();
	displayLangNone();

	var i = 0;
	var j = 0;
	var k = 0;
	var z = 0;
	var f = 0;

	// experiences
	$(".addExp").click(function() {
		$(".corp_experience").css("display", "inline");
		displayDegreeNone();
		displayTrainingNone();
		displayHobbieNone();
		displayLangNone();

		displayErrorExpNone();

		$(".addDiplTable").css("display", "inline");
		$(".addFormTable").css("display", "inline");
		$(".addLoisirTable").css("display", "inline");
		$(".addLangueTable").css("display", "inline");
		$(".addExpTable").css("display", "none");
	});

	$(".SaveExp")
			.click(
					function() {
						displayErrorExpNone();

						var erreur = 0;
						if ($("#cvExpJob").val() == '') {
							$(".CvExpJobError").css("display", "inline");
							erreur = 1;
						}
						if ($("#cvExpFirmName").val() == '') {
							$(".CvExpFirmNameError").css("display", "inline");
							erreur = 1;
						}

						if ($("#cvExpDomain").val() == '') {
							$(".CvExpDomainError").css("display", "inline");
							erreur = 1;
						}
						if ($("#cvExpBeginDate").val() == '') {
							$(".CvExpBeginDateError").css("display", "inline");
							erreur = 1;
						}
						if ($("#cvExpEndDate").val() == '') {
							$(".CvExpEndDateError").css("display", "inline");
							erreur = 1;
						}

						if (erreur == 0) {
							var recapExp = "<td><strong>"
									+ $("#cvExpJob").val()
									+ "</strong></td><td>"
									+ $("#cvExpFirmName").val() + "</td><td>"
									+ $("#cvExpDomain").val() + "</td><td>"
									+ $("#cvExpCountry").val() + " - "
									+ $("#cvExpCity").val() + "</td><td>"
									+ $("#cvExpBeginDate").val() + " - "
									+ $("#cvExpEndDate").val() + "</td>";

							var inputRecap = '<div id="ExpInput" style="display:none;"><input type="hidden" id="CVPostehidden" name="cvExpJob'
									+ i
									+ '" value="'
									+ $("#cvExpJob").val()
									+ '" />'
									+ '<input type="hidden" name="cvExpFirmName'
									+ i
									+ '" value="'
									+ $("#cvExpFirmName").val()
									+ '" />'
									+ '<input type="hidden" name="cvExpCountry'
									+ i
									+ '" value="'
									+ $("#cvExpCountry").val()
									+ '" />'
									+ '<input type="hidden" name="cvExpCity'
									+ i
									+ '" value="'
									+ $("#cvExpCity").val()
									+ '" />'
									+ '<input type="hidden" name="cvExpBeginDate'
									+ i
									+ '" value="'
									+ $("#cvExpBeginDate").val()
									+ '" />'
									+ '<input type="hidden" name="cvExpDomain'
									+ i
									+ '" value="'
									+ $("#cvExpDomain").val()
									+ '" />'
									+ '<input type="hidden" name="cvExpEndDate'
									+ i
									+ '" value="'
									+ $("#cvExpEndDate").val() + '" /></div>';

							$('.listeExperience').append(
									'<tr class="liste" id="experience_' + i
											+ '">' + recapExp + '</tr>');
							$('#experience_' + i).append(inputRecap);

							displayExpNone();
							$(".addExpTable").css("display", "inline");

							i++;
						}

					});

	$(".annuleExp").click(function() {
		displayExpNone();
		$(".addExpTable").css("display", "inline");
	});

	$(".addDip").click(function() {
		displayExpNone();
		displayTrainingNone();
		displayHobbieNone();
		displayLangNone();
		$(".corp_diplome").css("display", "inline");

		displayErrorDegreeNone();

		$(".addFormTable").css("display", "inline");
		$(".addLoisirTable").css("display", "inline");
		$(".addLangueTable").css("display", "inline");
		$(".addExpTable").css("display", "inline");
		$(".addDiplTable").css("display", "none");
	});

	$(".SaveDip")
			.click(
					function() {
						displayErrorDegreeNone();

						var erreur = 0;
						if ($("#cvDegreeName").val() == '') {
							$(".CvDegreeNameError").css("display", "inline");
							erreur = 1;
						}
						if ($("#cvDegreeDomain").val() == '') {
							$(".CvDegreeDomainError").css("display", "inline");
							erreur = 1;
						}
						if ($("#cvDegreeBeginDate").val() == '') {
							$(".CvDegreeBeginDateError").css("display",
									"inline");
							erreur = 1;
						}

						if ($("#cvDegreeEndDate").val() == '') {
							$(".CvDegreeEndDateError").css("display", "inline");
							erreur = 1;
						}
						if ($("#cvDegreeSchool").val() == '') {
							$(".CvDegreeSchoolError").css("display", "inline");
							erreur = 1;
						}

						if (erreur == 0) {
							var recapDegree = "<td><strong>"
									+ $("#cvDegreeName").val()
									+ "</strong></td>"
									+ $("#cvDegreeDomain").val() + "</td><td>"
									+ $("#cvDegreeSchool").val() + "</td><td>"
									+ $("#cvDegreeCountry").val() + " - "
									+ $("#cvDegreeCity").val() + "</td><td>"
									+ $("#cvDegreeBeginDate").val() + " - "
									+ $("#cvDegreeEndDate").val() + "</td>";

							var inputRecap = '<div id="DipInput" style="display:none;"><input type="hidden" name="cvDegreeName'
									+ j
									+ '" value="'
									+ $("#cvDegreeName").val()
									+ '" />'
									+ '<input type="hidden" name="cvDegreeDomain'
									+ j
									+ '" value="'
									+ $("#cvDegreeDomain").val()
									+ '" />'
									+ '<input type="hidden" name="cvDegreeSchool'
									+ j
									+ '" value="'
									+ $("#cvDegreeSchool").val()
									+ '" />'
									+ '<input type="hidden" name="cvDegreeCountry'
									+ j
									+ '" value="'
									+ $("#cvDegreeCountry").val()
									+ '" />'
									+ '<input type="hidden" name="cvDegreeCity'
									+ j
									+ '" value="'
									+ $("#cvDegreeCity").val()
									+ '" />'
									+ '<input type="hidden" name="cvDegreeBeginDate'
									+ j
									+ '" value="'
									+ $("#cvDegreeBeginDate").val()
									+ '" />'
									+ '<input type="hidden" name="cvDegreeEndDate'
									+ j
									+ '" value="'
									+ $("#cvDegreeEndDate").val()
									+ '" /></div>';

							$('.listeDiplome').append(
									'<tr class="liste" id="diplome_' + j + '">'
											+ recapDegree + '</tr>');
							$('#diplome_' + j).append(inputRecap);

							displayDegreeNone();
							$(".addDiplTable").css("display", "inline");

							j++;
						}
					});

	$(".annuleDip").click(function() {
		displayDegreeNone();
		$(".addDiplTable").css("display", "inline");
	});

	// formation
	$(".addForm").click(function() {
		displayExpNone();
		displayDegreeNone();
		displayHobbieNone();
		displayLangNone();
		$(".corp_formation").css("display", "inline");

		displayErrorTrainingNone();

		$(".addLoisirTable").css("display", "inline");
		$(".addLangueTable").css("display", "inline");
		$(".addExpTable").css("display", "inline");
		$(".addDiplTable").css("display", "inline");
		$(".addFormTable").css("display", "none");
	});

	$(".SaveForm")
			.click(
					function() {
						displayErrorTrainingNone();

						var erreur = 0;

						if ($("#cvTrainingInstitution").val() == '') {
							$(".CvTrainingInstitutionError").css("display",
									"inline");
							erreur = 1;
						}
						if ($("#cvTrainingObtainingDate").val() == '') {
							$(".CvTrainingObtainingDateError").css("display",
									"inline");
							erreur = 1;
						}
						if ($("#cvTrainingName").val() == '') {
							$(".CvTrainingNameError").css("display", "inline");
							erreur = 1;
						}

						if (erreur == 0) {
							var recapTraining = "<td><strong>"
									+ $("#cvTrainingName").val()
									+ "</strong></td><td>"
									+ $("#cvTrainingInstitution").val()
									+ "</td><td>"
									+ $("#cvTrainingCountry").val() + " - "
									+ $("#cvTrainingCity").val() + "</td><td>"
									+ $("#cvTrainingObtainingDate").val()
									+ "</td>";

							var inputRecap = '<div id="FormInput" style="display:none;"><input type="hidden" name="cvTrainingName'
									+ f
									+ '" value="'
									+ $("#cvTrainingName").val()
									+ '" />'
									+ '<input type="hidden" name="cvTrainingInstitution'
									+ f
									+ '" value="'
									+ $("#cvTrainingInstitution").val()
									+ '" />'
									+ '<input type="hidden" name="cvTrainingCountry'
									+ f
									+ '" value="'
									+ $("#cvTrainingCountry").val()
									+ '" />'
									+ '<input type="hidden" name="cvTrainingCity'
									+ f
									+ '" value="'
									+ $("#cvTrainingCity").val()
									+ '" />'
									+ '<input type="hidden" name="cvTrainingObtainingDate'
									+ f
									+ '" value="'
									+ $("#cvTrainingObtainingDate").val()
									+ '" /></div>';

							$('.listeFormation').append(
									'<tr class="liste" id="formation_' + f
											+ '">' + recapTraining + '</tr>');
							$('#formation_' + f).append(inputRecap);

							$(".addFormTable").css("display", "inline");
							displayTrainingNone();

							f++;
						}
					});
	$(".annuleForm").click(function() {
		displayTrainingNone();
		$(".addFormTable").css("display", "inline");
	});

	// loisirs
	$(".addLoisir").click(function() {
		$(".corp_loisir").css("display", "inline");
		displayExpNone();
		displayDegreeNone();
		displayTrainingNone();
		displayLangNone();

		displayErrorHobbyNone();

		$(".addLangueTable").css("display", "inline");
		$(".addExpTable").css("display", "inline");
		$(".addDiplTable").css("display", "inline");
		$(".addFormTable").css("display", "inline");
		$(".addLoisirTable").css("display", "none");
	});

	$(".SaveLoisir")
			.click(
					function() {
						displayErrorHobbyNone();

						var erreur = 0;
						if ($("#cvHobbyName").val() == '') {
							$(".CvHobbyNameError").css("display", "inline");
							erreur = 1;
						}

						if (erreur == 0) {
							var recapHobby = "<td><strong>" + $("#cvHobbyName").val()
									+ "</strong></td>";

							var inputRecap = '<div id="loisirInput" style="display:none;"><input type="hidden" name="cvHobbyName'
									+ k
									+ '" value="'
									+ $("#cvHobbyName").val()
									+ '" /></div>';

							$('.listeLoisir').append(
									'<tr class="liste" id="loisir_' + k + '">'
											+ recapHobby + '</tr>');
							$('#loisir_' + k).append(inputRecap);

							$(".addLoisirTable").css("display", "inline");
							displayHobbieNone();

							k++;
						}

					});
	$(".annuleLoisir").click(function() {
		displayHobbieNone();
		$(".addLoisirTable").css("display", "inline");
	});

	// langues
	$(".addLangue").click(function() {
		displayExpNone();
		displayDegreeNone();
		displayTrainingNone();
		displayHobbieNone();
		$(".corp_langue").css("display", "inline");

		displayErrorLangNone();

		$(".addExpTable").css("display", "inline");
		$(".addDiplTable").css("display", "inline");
		$(".addFormTable").css("display", "inline");
		$(".addLoisirTable").css("display", "inline");
		$(".addLangueTable").css("display", "none");
	});

	$(".SaveLangue")
			.click(
					function() {
						displayErrorLangNone();
						$(".CvLangNameError").css("display", "none");

						var erreur = 0;
						if ($("#cvLangName").val() == '') {
							$(".CvLangNameError").css("display", "inline");
							erreur = 1;
						}
						if (erreur == 0) {
							var recapLang = "<td><strong>"
									+ $("#cvLangName").val() + "</strong> - "
									+ $("#cvLangLevel").val() + "</td>";

							var inputRecap = '<div id="LangueInput" style="display:none;"><input type="hidden" name="cvLangName'
									+ z
									+ '" value="'
									+ $("#cvLangName").val()
									+ '" />'
									+ '<input type="hidden" name="cvLangLevel'
									+ z
									+ '" value="'
									+ $("#cvLangLevel").val()
									+ '" /></div>';

							$('.listeLangues').append(
									'<tr class="liste" id="langue_' + z + '">'
											+ recapLang + '</tr>');
							$('#langue_' + z).append(inputRecap);

							displayLangNone();
							$(".addLangueTable").css("display", "inline");

							z++;
						}

					});

	$(".annuleLangue").click(function() {
		displayLangNone();
		$(".addLangueTable").css("display", "inline");
	});

	$("#envoi").click(
			function() {
				var nbExp = '<input type="hidden" name="nbExp" value="' + i
						+ '" />';
				var nbForm = '<input type="hidden" name="nbForm" value="' + f
						+ '" />';
				var nbLang = '<input type="hidden" name="nbLang" value="' + k
						+ '" />';
				var nbHobby = '<input type="hidden" name="nbHobbie" value="'
						+ z + '" />';
				var nbDegree = '<input type="hidden" name="nbDegree" value="'
						+ j + '" />';
				$('.listeExperience').append(nbExp);
				$('.listeFormation').append(nbForm);
				$('.listeLangues').append(nbLang);
				$('.listeLoisir').append(nbHobby);
				$('.listeDiplome').append(nbDegree);
			});

});

function displayExpNone() {
	$(".corp_experience").css("display", "none");
	$(".CvExpJobError").css("display", "none");
	$(".cvExpFirmNameError").css("display", "none");
	$(".CvExpBeginDateError").css("display", "none");
	$(".CvExpEndDateError").css("display", "none");
	$(".CvExpDomainError").css("display", "none");
	
	$("#cvExpJob").val('');
	$("#cvExpFirmName").val('');
	$("#cvExpDomain").val('');
	$("#cvExpCountry").val('');
	$("#cvExpCity").val('');
	$("#cvExpBeginDate").val('');
	$("#cvExpEndDate").val('');
}

function displayDegreeNone() {
	$(".corp_diplome").css("display", "none");
	$(".CvDegreeSchoolError").css("display", "none");
	$(".CvDegreeBeginDateError").css("display", "none");
	$(".CvDegreeEndDateError").css("display", "none");
	$(".CvDegreeDomainError").css("display", "none");
	$(".CvDegreeNameError").css("display", "none");
	
	$("#cvDegreeName").val('');
	$("#cvDegreeDomain").val('');
	$("#cvDegreeSchool").val('');
	$("#cvDegreeCity").val('');
	$("#cvDegreeCountry").val('');
	$("#cvDegreeBeginDate").val('');
	$("#cvDegreeEndDate").val('');
}

function displayTrainingNone() {
	$(".corp_formation").css("display", "none");
	$(".CvTrainingInstitutionError").css("display", "none");
	$(".CvTrainingNameError").css("display", "none");
	$(".CvTrainingObtainingDateError").css("display", "none");
	
	$("#cvTrainingName").val('');
	$("#cvTrainingInstitution").val('');
	$("#cvTrainingCountry").val('');
	$("#cvTrainingCity").val('');
	$("#cvTrainingObtainingDate").val('');
}

function displayHobbieNone() {
	$(".corp_loisir").css("display", "none");
	$(".CvHobbyNameError").css("display", "none");
	
	$("#cvHobbyName").val('');
}

function displayLangNone() {
	$(".corp_langue").css("display", "none");
	$(".CvLangNameError").css("display", "none");
	
	$("#cvLangName").val('');
}

function displayErrorExpNone() {
	$(".CvExpJobError").css("display", "none");
	$(".CvExpFirmNameError").css("display", "none");
	$(".CvExpBeginDateError").css("display", "none");
	$(".CvExpEndDateError").css("display", "none");
	$(".CvExpDomainError").css("display", "none");
}

function displayErrorDegreeNone() {
	$(".CvDegreeSchoolError").css("display", "none");
	$(".CvDegreeBeginDateError").css("display", "none");
	$(".CvDegreeEndDateError").css("display", "none");
	$(".CvDegreeDomainError").css("display", "none");
	$(".CvDegreeNameError").css("display", "none");
}

function displayErrorTrainingNone() {
	$(".CvTrainingInstitutionError").css("display", "none");
	$(".CvTrainingNameError").css("display", "none");
	$(".CvTrainingObtainingDateError").css("display", "none");
}

function displayErrorHobbyNone() {
	$(".CvHobbyNameError").css("display", "none");
}

function displayErrorLangNone() {
	$(".CvLangNameError").css("display", "none");
}
