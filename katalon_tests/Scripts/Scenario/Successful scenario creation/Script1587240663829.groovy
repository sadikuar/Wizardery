import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('http://127.0.0.1:8081/')

WebUI.click(findTestObject('Page_Wizardery/a_Signin (2)'))

WebUI.setText(findTestObject('Page_Wizardery/input_Email address_email (3)'), 'user@wizardery.ch')

WebUI.setEncryptedText(findTestObject('Page_Wizardery/input_Password_password (3)'), '8SQVv/p9jVScEs4/2CZsLw==')

WebUI.click(findTestObject('Page_Wizardery/input_here_submit (3)'))

WebUI.click(findTestObject('Page_Wizardery/a_Learn more (3)'))

WebUI.click(findTestObject('Page_Wizardery/a_Add a scenario (2)'))

WebUI.setText(findTestObject('Page_Wizardery/input_Name_name (3)'), 'My scenario')

WebUI.setText(findTestObject('Page_Wizardery/textarea_Description_description (3)'), 'My **new** scenario')

WebUI.setText(findTestObject('Page_Wizardery/input_Number of players (min advised and ma_725227 (2)'), '1')

WebUI.setText(findTestObject('Page_Wizardery/input_Number of players (min advised and ma_ab5eb4 (2)'), '2')

WebUI.setText(findTestObject('Page_Wizardery/input_Number of players (min advised and ma_fc8787 (2)'), '3')

WebUI.selectOptionByValue(findTestObject('Page_Wizardery/select_EasyMediumDifficultImpossible (2)'), 'difficult', true)

WebUI.setText(findTestObject('Page_Wizardery/input_Time to complete (in minutes)_timeApp_72c4f5 (2)'), '120')

WebUI.setText(findTestObject('Page_Wizardery/textarea_Quests_quests (2)'), '* don\'t die')

WebUI.click(findTestObject('Page_Wizardery/input_here_submit (3)'))

WebUI.click(findTestObject('Page_Wizardery/a_Learn more_1 (2)'))

WebUI.click(findTestObject('Page_Wizardery/input_User_favoriteSubmit (2)'))

WebUI.click(findTestObject('Page_Wizardery/input_User_unfavoriteSubmit (2)'))

WebUI.click(findTestObject('Page_Wizardery/input_User_updateSubmit (3)'))

WebUI.click(findTestObject('Page_Wizardery/input_Name_name (3)'))

WebUI.setText(findTestObject('Page_Wizardery/input_Name_name (3)'), 'My new scenario')

WebUI.setText(findTestObject('Page_Wizardery/textarea_My new scenario (2)'), 'My **newest** scenario')

WebUI.setText(findTestObject('Page_Wizardery/input_Number of players (min advised and ma_725227 (2)'), '2')

WebUI.setText(findTestObject('Page_Wizardery/input_Number of players (min advised and ma_ab5eb4 (2)'), '3')

WebUI.setText(findTestObject('Page_Wizardery/input_Number of players (min advised and ma_fc8787 (2)'), '4')

WebUI.selectOptionByValue(findTestObject('Page_Wizardery/select_EasyMediumDifficultImpossible (2)'), 'impossible', true)

WebUI.setText(findTestObject('Page_Wizardery/input_Time to complete (in minutes)_timeApp_72c4f5 (2)'), '240')

WebUI.setText(findTestObject('Page_Wizardery/textarea_ dont die (2)'), '* don\'t die\n* git gud')

WebUI.setText(findTestObject('Page_Wizardery/textarea_Patch note_patchNote (2)'), '#21.04.2020\nUpdated quests')

WebUI.click(findTestObject('Page_Wizardery/input_here_submit (3)'))

WebUI.click(findTestObject('Object Repository/Page_Wizardery/h2_My new scenario'))

WebUI.click(findTestObject('Page_Wizardery/button_Delete (3)'))

WebUI.click(findTestObject('Page_Wizardery/button_Confirm delete (3)'))

