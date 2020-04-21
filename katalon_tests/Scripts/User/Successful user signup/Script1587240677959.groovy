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

WebUI.click(findTestObject('Page_Wizardery/a_Signin'))

WebUI.click(findTestObject('Page_Wizardery/a_here'))

WebUI.setText(findTestObject('Page_Wizardery/input_Email address_email'), 'test@test.ch')

WebUI.setText(findTestObject('Page_Wizardery/input_Username_username'), 'test')

WebUI.setEncryptedText(findTestObject('Page_Wizardery/input_Password_password'), 'YsxTccsmuDoDBwXzvpNmKQ==')

WebUI.setEncryptedText(findTestObject('Page_Wizardery/input_Confirm password_passwordConfirm'), 'YsxTccsmuDoDBwXzvpNmKQ==')

WebUI.setText(findTestObject('Page_Wizardery/textarea_Description_description'), 'This is my description')

WebUI.click(findTestObject('Page_Wizardery/input_Public_submit'))

WebUI.click(findTestObject('Page_Wizardery/img_Create game_img-thumbnail wiz-avatar'))

WebUI.click(findTestObject('Page_Wizardery/a_Edit'))

WebUI.doubleClick(findTestObject('Page_Wizardery/input_Username_username'))

WebUI.setText(findTestObject('Page_Wizardery/input_Username_username'), 'toto')

WebUI.setEncryptedText(findTestObject('Page_Wizardery/input_Password_password'), 'igHB5f1GapZBxqyAXvLf5g==')

WebUI.setEncryptedText(findTestObject('Page_Wizardery/input_Confirm password_passwordConfirm'), 'igHB5f1GapZBxqyAXvLf5g==')

WebUI.setText(findTestObject('Object Repository/Page_Wizardery/textarea_This is my description'), 'This is toto\'s description')

WebUI.click(findTestObject('Object Repository/Page_Wizardery/input_Public_public'))

WebUI.click(findTestObject('Page_Wizardery/input_Public_submit'))

WebUI.click(findTestObject('Page_Wizardery/a_Logout'))

WebUI.setText(findTestObject('Page_Wizardery/input_Email address_email_1'), 'test@test.ch')

WebUI.setEncryptedText(findTestObject('Page_Wizardery/input_Password_password_1'), 'YsxTccsmuDoDBwXzvpNmKQ==')

WebUI.click(findTestObject('Page_Wizardery/input_Public_submit'))

WebUI.setText(findTestObject('Page_Wizardery/input_Email address_email_1'), 'test@test.ch')

WebUI.setEncryptedText(findTestObject('Page_Wizardery/input_Password_password_1'), 'igHB5f1GapZBxqyAXvLf5g==')

WebUI.click(findTestObject('Page_Wizardery/input_Public_submit'))

WebUI.click(findTestObject('Page_Wizardery/img_Create game_img-thumbnail wiz-avatar'))

WebUI.click(findTestObject('Page_Wizardery/button_Delete Profile'))

WebUI.setText(findTestObject('Page_Wizardery/input_Confirm your email_confirmEmail'), 'test@test.ch')

WebUI.click(findTestObject('Page_Wizardery/button_Confirm delete'))

WebUI.click(findTestObject('Page_Wizardery/a_Signin'))

WebUI.setText(findTestObject('Page_Wizardery/input_Email address_email_1'), 'test@test.ch')

WebUI.setEncryptedText(findTestObject('Page_Wizardery/input_Password_password_1'), 'igHB5f1GapZBxqyAXvLf5g==')

WebUI.click(findTestObject('Page_Wizardery/input_Public_submit'))

