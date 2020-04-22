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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('http://127.0.0.1:8081/')

WebUI.click(findTestObject('Page_Wizardery/a_Create game (1)'))

WebUI.setText(findTestObject('Page_Wizardery/input_Email address_email (1)'), 'user@wizardery.ch')

WebUI.setEncryptedText(findTestObject('Page_Wizardery/input_Password_password (1)'), '8SQVv/p9jVScEs4/2CZsLw==')

WebUI.click(findTestObject('Page_Wizardery/input_here_submit (1)'))

WebUI.click(findTestObject('Page_Wizardery/a_Create game (1)'))

WebUI.setText(findTestObject('Page_Wizardery/input_Name_name (1)'), 'My game')

WebUI.setText(findTestObject('Page_Wizardery/textarea_Description_description (1)'), 'My **new** game')

WebUI.setText(findTestObject('Page_Wizardery/textarea_Rules_rules (1)'), '* don\'t cheat')

WebUI.click(findTestObject('Page_Wizardery/input_here_submit (1)'))

WebUI.click(findTestObject('Page_Wizardery/input_User_favoriteSubmit (1)'))

WebUI.click(findTestObject('Page_Wizardery/img_Create game_img-thumbnail wiz-avatar (1)'))

WebUI.click(findTestObject('Page_Wizardery/a_Learn more (1)'))

WebUI.click(findTestObject('Page_Wizardery/input_User_unfavoriteSubmit (1)'))

WebUI.click(findTestObject('Page_Wizardery/input_User_updateSubmit (1)'))

WebUI.setText(findTestObject('Page_Wizardery/input_Name_name (1)'), 'My new game')

WebUI.setText(findTestObject('Page_Wizardery/textarea_My new game (1)'), 'My **newest** game')

WebUI.setText(findTestObject('Object Repository/Page_Wizardery/textarea_ dont cheat'), '* don\'t cheat\n* please don\'t')

WebUI.click(findTestObject('Page_Wizardery/input_here_submit (1)'))

WebUI.click(findTestObject('Object Repository/Page_Wizardery/h2_My new game'))

WebUI.click(findTestObject('Page_Wizardery/button_Delete (1)'))

WebUI.click(findTestObject('Page_Wizardery/button_Confirm delete (1)'))

