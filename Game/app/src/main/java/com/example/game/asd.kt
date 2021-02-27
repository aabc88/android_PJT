package com.example.game
import android.R
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.*
import android.media.MediaPlayer
import android.provider.BaseColumns
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.*

// //공용 클래스 Game의 뷰 확장
// class Game(con: Context?, at: AttributeSet?) :
// View(con, at) {
// /* 테이블 내용을 정의한다. */
// object FeedEntry : BaseColumns {
// const val TABLE_NAME = "entry"
// const val _ID = "_id"
// const val COLUMN_NAME_TITLE = "title"
// const val COLUMN_NAME_SUBTITLE = "subtitle"
// const val COLUMN_NAME_SUBTITLE2 = "subtitle2"
// }
//
// internal inner class FeedReaderDbHelper(context: Context?) :
// SQLiteOpenHelper(
// context,
// Game.FeedReaderDbHelper.Companion.DATABASE_NAME,
// null,
// Game.FeedReaderDbHelper.Companion.DATABASE_VERSION
// ) {
// override fun onCreate(db: SQLiteDatabase) {
// db.execSQL(Game.Companion.SQL_CREATE_ENTRIES)
// }
//
// override fun onUpgrade(
// db: SQLiteDatabase,
// oldVersion: Int,
// newVersion: Int
// ) {
// // This database is only a cache for online data, so its upgrade policy is
// // to simply to discard the data and start over
// db.execSQL(Game.Companion.SQL_DELETE_ENTRIES)
// onCreate(db)
// }
//
// override fun onDowngrade(
// db: SQLiteDatabase,
// oldVersion: Int,
// newVersion: Int
// ) {
// onUpgrade(db, oldVersion, newVersion)
// }
//
// companion object {
// // If you change the database schema, you must increment the database version.
// const val DATABASE_VERSION = 1 //DB 버전
// const val DATABASE_NAME = "FeedReader.db" //DB 이름
// }
// }
//
// var dbHelper: Game.FeedReaderDbHelper = Game.FeedReaderDbHelper(context)
//
// //스크린 폭, 높이 정보를 저장할 정수형 변수
// var scrw = 0
// var scrh = 0
//
// //캐릭터 이미지 기준점의 x좌표와 y좌표 위치 값을 저장할 실수형 변수
// var xd = 0f
// var yd = 0f
//
// //적군의 x좌표 값을 저장할 실수형 변수
// var rxd = FloatArray(3)
//
// //적군의 y좌표 값을 저장할 실수형 변수
// var ryd = FloatArray(3)
//
// //적군의 이동을 위한 카운트 수를 저장할 정수형 변수
// var count2 = IntArray(3)
//
// //적군의 생명 값을 저장할 정수형 변수
// var life = IntArray(3)
//
// //적군의 이동 방향을 저장할 문자열 변수
// private val RectDirButton = arrayOfNulls<String>(3)
//
// // 위에서 3은 적군을 화면에 최대 3개까지만 표시할 것을 의미합니다.
// //캐릭터의 이동을 위한 카운트 수를 저장할 정수형 변수
// var count = 0
//
// //DB저장
// var save = 0
// var savecount = 1
//
// //캐릭터 방향키 버튼 클릭 유무 (false(=0) 또는 true(=1) 유무로 판단한다.)
// var start = false
//
// //캐릭터가 정지 상태에 있는 동안 어떤 방향키를 클릭했는지 저장할 문자열 변수
// private var DirButton: String? = null
//
// //캐릭터가 이동하고 있는 상태에서 어떤 방향키를 클릭했는지 저장할 문자열 변수
// private var DirButton2: String? = null
//
// //화면에 표시할 수 있는 최대 미사일 수를 저장할 정수형 변수
// var missileCount = 0
//
// //미사일 번호를 저장할 정수형 변수
// var missileNum = IntArray(10)
//
// //미사일 x위치를 저장할 정수형 변수
// var mx = FloatArray(10)
//
// //미사일 y위치를 저장할 정수형 변수
// var my = FloatArray(10)
//
// //미사일 방향을 저장할 정수형 변수
// var md = IntArray(10)
//
// //미사일 초기 방향을 저장할 정수형 변수. 4번 즉 아래 방향으로 설정함.
// var MD = 4
//
// //랜덤 변수
// var random = Random()
//
// //위에서 10은 화면에 최대 10개까지의 미사일을 표시할 것을 의미합니다.
// //화면의 폭을 왼쪽과 오른쪽 부분 중 어느 곳을 클릭했는지 판별하는데 사용할 정수형 변수
// var n = 0
//
// //p라는 이름의 페인트 변수 설정
// var p = Paint()
//
// //T라는 이름의 게임 쓰레드를 설정.
// private var T: GameThread? = null
//
// //미디어 플레이어를 사용하기위한 변수 설정.
// var mp: MediaPlayer
//
// //뷰의 크기가 변경될 때 호출
// override fun onSizeChanged(sw: Int, sh: Int, esw: Int, esh: Int) {
// //부모 클래스의 멤버 변수를 참조한다.
// super.onSizeChanged(sw, sh, esw, esh)
// //뷰의 폭 정보 저장
// scrw = sw
// //뷰의 높이 정보 저장
// scrh = sh
//
// //적군의 생명력 저장
// for (i in 0..2) {
// //적군이 미사일에 2번까지 견디도록 설정
// life[i] = 2
// }
//
// //쓰레드 값이 비었다면
// if (T == null) {
// //GameThread()함수를 돌린 값을 넣어줌
// T = GameThread()
// //쓰레드 시작
// T!!.start()
// }
// }
//
// // 뷰가 윈도우에서 분리될 때마다 발생.
// override fun onDetachedFromWindow() {
// //쓰레드의 run 값으로 false 값을 줌.
// T!!.run = false
// //부모 클래스의 멤버 변수를 참조
// super.onDetachedFromWindow()
// }
//
// //캔버스 위에 그리기
// override fun onDraw(canvas: Canvas) {
//
// //테이블 내에 있는 DB 삭제
// if (count == 0 && DirButton === "Up") {
// val db: SQLiteDatabase = dbHelper.getWritableDatabase()
// db.execSQL("DELETE FROM entry")
// savecount = 1
// }
//
// //테이블 title과 subtitle에 저장할 내용
// val title = "" + savecount
// val subtitle = "5678$savecount"
// val subtitle2 = "서브2 :$savecount"
//
// //DB 저장
// if (count == 0 && DirButton === "Left" && save == 0) {
// // Gets the data repository in write mode
// val db: SQLiteDatabase = dbHelper.getWritableDatabase()
//
// // Create a new map of values, where column names are the keys
// val values = ContentValues()
// values.put(Game.FeedEntry.COLUMN_NAME_TITLE, title)
// values.put(Game.FeedEntry.COLUMN_NAME_SUBTITLE, subtitle)
// values.put(Game.FeedEntry.COLUMN_NAME_SUBTITLE2, subtitle2)
//
// // Insert the new row, returning the primary key value of the new row
// val newRowId = db.insert(Game.FeedEntry.TABLE_NAME, null, values)
// save = 1
// savecount += 1
// db.close()
// }
// if (count == 0 && DirButton === "Down") {
// save = 0
// }
//
// //DB 읽기
// if (count == 0 && DirButton === "Right") {
// val db: SQLiteDatabase = dbHelper.getReadableDatabase()
// val a = "1"
// val cursor1 =
// db.rawQuery("SELECT * FROM entry WHERE _id =$a", null)
// if (cursor1.moveToFirst()) {
// val Title = cursor1.getString(2)
// p.textSize = scrh / 16.toFloat()
// canvas.drawText("" + Title, 0f, 400f, p)
// }
// cursor1.close()
// db.close()
// }
//
// //main에 12개까지의 비트맵 정보를 저장함
// val main = arrayOfNulls<Bitmap>(12)
// //missile에 10개가지의 비트맵 정보를 저장함
// val missile = arrayOfNulls<Bitmap>(10)
//
// //미사일 수는 0으로 설정
// missileCount = 0
//
// //i가 0부터 10보다 작은 동안 i가 1씩 증가하면서 반복문 처리
// for (i in 0..9) {
// //i번째 미사일 그림 파일의 경로를 배열에 저장해줌.
// missile[i] = BitmapFactory.decodeResource(resources, R.drawable.missile)
// //i번째 미사일 그림 파일의 화면에 나타낼 크기 설정
// missile[i] = Bitmap.createScaledBitmap(missile[i], scrw / 16, scrw / 16, true)
//
// //i번째 미사일 번호가 0이라는 값을 품고 있으면(미사일 비활성)
// if (missileNum[i] == 0) {
// //미사일 카운트를 1 증가
// missileCount += 1
// }
//
// //i번째 미사일 번호가 1이라는 값을 품고 있으면(미사일 활성)
// if (missileNum[i] == 1) {
// //i번재 미사일 그림을 i번째 mx, i번째 my지점에 그리기
// canvas.drawBitmap(missile[i], mx[i], my[i], null)
// //i번째 md가 1이라면
// if (md[i] == 1) {
// //i번째 mx값을 scrw/64만큼 감소
// mx[i] -= scrw / 64
// }
// //i번째 md가 2라면
// if (md[i] == 2) {
// //i번째 mx값을 scrw/64만큼 증가
// mx[i] += scrw / 64
// }
// //i번재 md가 3이라면
// if (md[i] == 3) {
// //i번째 my값을 scrh/32만큼 감소
// my[i] -= scrh / 32
// }
// //i번째 md가 4라면
// if (md[i] == 4) {
// //i번재 my값을 scrh/32만큼 증가
// my[i] += scrh / 32
// }
//
// //j는 0부터 j는 3보다 작은 동안 1씩 증가
// for (j in 0..2) {
// //생명력이 존재하는 j번째 적군 캐릭터가 i번째 미사일에 맞았다면
// if (life[j] > 0 && mx[i] <= scrw / 2 + (scrw - scrw % 64) / 8 + rxd[j] && mx[i] >= scrw / 2 + rxd[j] && my[i] >= scrh / 2 + ryd[j] && my[i] <= scrh / 2 + (scrh - scrh % 32) / 4 + ryd[j]
// ) {
// //j번째 적군의 생명력 1감소
// life[j] -= 1
// //i번째 미사일을 비활성화 시킨다.
// missileNum[i] = 0
// }
// }
// }
//
// //i번째 mx값이 scrw-scrw/16보다 크거나 i번째 mx값이 0보다 작거나 i번째 my값이 scrh-scrw/16보다 크거나 i번째 my값이 0보다 작다면
// if (mx[i] > scrw - scrw / 16 || mx[i] < 0 || my[i] > scrh - scrw / 16 || my[i] < 0
// ) {
// //i번째 미사일을 비활성화 시킨다.
// missileNum[i] = 0
// }
// }
//
// //i는 0부터 12보다 작은 동안 1씩 증가하면서 반복문을 처리함
// for (i in 0..11) {
// //캐릭터 그림 파일의 경로를 배열에 저장해줌.
// main[i] = BitmapFactory.decodeResource(resources, R.drawable.character01 + i)
//
// //캐릭터 그림 파일의 크기를 설정해줌. 폭은 (scrw-scrw를 64로 나눈 값의 나머지) 나누기 8, 높이는 (scrh-scrh을 32로 나눈 값의 나머지) 나누기 4
// main[i] = Bitmap.createScaledBitmap(
// main[i],
// (scrw - scrw % 64) / 8,
// (scrh - scrh % 32) / 4,
// true
// )
//
// //i와 n의 값이 같다면
// if (i == n) {
// // 캐릭터 그림파일을 scrw/2+xd, scrh/2+yd 지점을 기준으로 그려줌.
// canvas.drawBitmap(
// main[i],
// scrw.toFloat() / 2 + xd,
// scrh.toFloat() / 2 + yd,
// null
// )
// }
// }
//
// //i는 0부터 3보다 작은 동안 i는 1씩 증가
// for (i in 0..2) {
// //rect3라는 이름의 페인트 변수 생성
// val rect3 = Paint()
// //rect3 페인트 변수 색상을 빨간색으로 설정
// rect3.color = Color.RED
// //적군의 생명이 있다면, 적군을 그려준다. (왼쪽, 위, 오른쪽, 아래) 좌표를 사용하여 상자를 그려준다.
// if (life[i] > 0) canvas.drawRect(
// scrw / 2 + rxd[i],
// scrh / 2 + ryd[i],
// scrw / 2 + (scrw - scrw % 64) / 8 + rxd[i],
// scrh / 2 + (scrh - scrh % 32) / 4 + ryd[i],
// rect3
// )
// }
//
// //IS라는 이름의 1행 4열 비트맵 변수를 생성
// val IS =
// Array(1) { arrayOfNulls<Bitmap>(4) }
// //I라는 이름의 비트맵 변수에 방향키 그림 파일의 경로를 저장
// var I = BitmapFactory.decodeResource(context.resources, R.drawable.dir)
// //방향키 그림 파일의 크기를 설정
// I = Bitmap.createScaledBitmap(I!!, scrw / 8, scrh, true)
// //i는 0부터 i는 1보다 작을때까지 i는 1씩 증가
// for (i in 0..0) {
// //j는 0부터 j는 4보다 작을때까지 j는 1씩 증가
// for (j in 0..3) {
// //방향키 그림의 분할할 i번째,j번째 기준점 및 크기를 설정함
// IS[i][j] =
// Bitmap.createBitmap(I, i * scrw / 8, j * scrh / 4, scrw / 8, scrh / 4)
// }
// }
// //IS 1행 1열 방향키 그림을 scrw/8, scrh-scrh/2 기준점으로 삼고 그려줌
// canvas.drawBitmap(IS[0][0]!!, scrw / 8.toFloat(), scrh - scrh / 2.toFloat(), null)
// //IS 1행 2열 방향키 그림을 0, scrh-scrh/4 기준점으로 삼고 그려줌
// canvas.drawBitmap(IS[0][1]!!, 0f, scrh - scrh / 4.toFloat(), null)
// //IS 1행 3열 방향키 그림을 scrw/4, scrh-scrh/4 기준점으로 삼고 그려줌
// canvas.drawBitmap(IS[0][2]!!, scrw / 4.toFloat(), scrh - scrh / 4.toFloat(), null)
// //IS 1행 4열 방향키 그림을 scrw/8, scrh-scrh/4 기준점으로 삼고 그려줌
// canvas.drawBitmap(IS[0][3]!!, scrw / 8.toFloat(), scrh - scrh / 4.toFloat(), null)
//
// //0, scrh/16지점을 기준으로 적군의 생명력을 텍스트로 표시
// }
//
// //터치이벤트 처리
// override fun onTouchEvent(event: MotionEvent): Boolean {
//
// //만약 회면을 터치했다면
// if (event.action == MotionEvent.ACTION_MOVE || event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_POINTER_DOWN) {
// //오른쪽 버튼을 클릭했다면
// if (event.x.toInt() > scrw / 4 && event.x as Int<scrw * 3 / 8 && event.y as Int<scrh && event.y
// .toInt() > scrh - scrh / 4
// ) {
// //버튼을 클릭한 상태가 아니고, 캐릭터가 이동중이 아니라면
// if (start == false && count == 0) {
// //버튼을 클릭했음을 알림
// start = true
// //오른쪽 버튼을 클릭헸음을 알림
// DirButton = "Right"
// }
// //오른쪽 버튼을 클릭했음을 알림
// DirButton2 = "Right"
// } else if (event.x.toInt() > 0 && event.x as Int<scrw / 8 && event.y as Int<scrh && event.y
// .toInt() > scrh - scrh / 4
// ) {
// //버튼을 클릭한 상태가 아니고, 캐릭터가 이동중이 아니라면
// if (start == false && count == 0) {
// //버튼을 클릭했음을 알림
// start = true
// //왼쪽 버튼을 클릭헸음을 알림
// DirButton = "Left"
// }
// //왼쪽 버튼을 클릭헸음을 알림
// DirButton2 = "Left"
// } else if (event.x.toInt() > scrw / 8 && event.x as Int<scrw / 4 && event.y as Int<scrh - scrh / 4 && event.y
// .toInt() > scrh - scrh / 2
// ) {
// //버튼을 클릭한 상태가 아니고, 캐릭터가 이동중이 아니라면
// if (start == false && count == 0) {
// //버튼을 클릭했음을 알림
// start = true
// //위쪽 버튼을 클릭헸음을 알림
// DirButton = "Up"
// }
// //위쪽 버튼을 클릭헸음을 알림
// DirButton2 = "Up"
// } else if (event.x.toInt() > scrw / 8 && event.x as Int<scrw / 4 && event.y as Int<scrh && event.y
// .toInt() > scrh - scrh / 4
// ) {
// //버튼을 클릭한 상태가 아니고, 캐릭터가 이동중이 아니라면
// if (start == false && count == 0) {
// //버튼을 클릭했음을 알림
// start = true
// //아래 버튼을 클릭헸음을 알림
// DirButton = "Down"
// }
// //아래 버튼을 클릭헸음을 알림
// DirButton2 = "Down"
// } else if (event.x.toInt() > scrw / 2) {
// //i는 0부터 i는 10보다 작은 동안 i는 1씩 증가
// for (i in 0..9) {
// //i번째 미사일이 비활성화 상태라면
// if (missileNum[i] == 0) {
// //i번째 미사일에 x좌표값을 부여한다.
// mx[i] = scrw / 2 + (scrw / 8 - scrw / 16) / 2 + xd
// //i번째 미사일에 y좌표값을 부여한다.
// my[i] = scrh / 2 + (scrh / 4 - scrw / 16) / 2 + yd
// //i번째 미사일 방향을 저장한다.
// md[i] = MD
// //i번째 미사일을 활성화시킨다.
// missileNum[i] = 1
// //잔여 미사일 숫자가 0이 아니라면, 미사일 숫자를 1감소시킨다.
// if (missileCount != 0) missileCount -= 1
// //함수 종료
// break
// }
// }
// } else {
// //버튼을 클릭하고 있는 상태가 아니라고 선언함
// start = false
// }
// }
//
// //화면에서 손을 땠을때
// if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_POINTER_UP) {
// //오른쪽 버튼 위에서 손을 땠다면
// if (event.x.toInt() > scrw / 4 && event.x as Int<scrw * 3 / 8 && event.y as Int<scrh && event.y
// .toInt() > scrh - scrh / 4
// ) {
// //방향키 버튼을 클릭하고 있는 상태가 아님을  선언
// start = false
// } else if (event.x.toInt() > 0 && event.x as Int<scrw / 8 && event.y as Int<scrh && event.y
// .toInt() > scrh - scrh / 4
// ) {
// //방향키 버튼을 클릭하고 있는 상태가 아님을  선언
// start = false
// } else if (event.x.toInt() > scrw / 8 && event.x as Int<scrw / 4 && event.y as Int<scrh - scrh / 4 && event.y
// .toInt() > scrh - scrh / 2
// ) {
// //방향키 버튼을 클릭하고 있는 상태가 아님을  선언
// start = false
// } else if (event.x.toInt() > scrw / 8 && event.x as Int<scrw / 4 && event.y as Int<scrh && event.y
// .toInt() > scrh - scrh / 4
// ) {
// //방향키 버튼을 클릭하고 있는 상태가 아님을  선언
// start = false
// }
// }
//
// //return 한다.
// return true
// }
//
// //게임 쓰레드 클래스에 쓰레드 확장
// internal inner class GameThread : Thread() {
// //run은 0 또는 1의 값을 가질 수 있으며, true 값을 넣어줌. (true = 1, false = 0)
// var run = true
//
// //run 함수를 만들어줌
// override fun run() {
// //run의 값이 1인 동안
// while (run) {
// try {
// //뷰에서 이미지를 분리시킨다.
// postInvalidate()
// //카운트가 8이라면
// if (count == 8) {
// //카운트는 0
// count = 0
// //방향키 정보를 저장한다.
// DirButton = DirButton2
// }
// //i는 0부터 3보다 작은 동안 i는 1씩 증가
// for (i in 0..2) {
// //i번째 count2가 8이라면
// if (count2[i] == 8) {
// //i번째 count2는 0
// count2[i] = 0
// }
// //i번째 count2가 0이라면
// if (count2[i] == 0) {
// //정수형 변수r은 1~4 사이의 정수 값을 가진다.
// // (4-1 + 1) + 1 -> 1~4
// // (4-3 + 1) + 3 -> 3~4
// val r = random.nextInt(4 - 1 + 1) + 1
// //r이 1이라면
// if (r == 1) {
// //i번째 RectDirButton은 왼쪽임을 저장
// RectDirButton[i] = "Left"
// }
// //r이 2라면
// if (r == 2) {
// //i번째 RectDirButton은 오른쪽임을 저장
// RectDirButton[i] = "Right"
// }
// //r이 3이라면
// if (r == 3) {
// //i번째 RectDirButton은 위쪽임을 저장
// RectDirButton[i] = "Up"
// }
// //r이 4라면
// if (r == 4) {
// //i번째 RectDirButton은 아래쪽임을 저장
// RectDirButton[i] = "Down"
// }
// }
// //i번째 생명이 남아있고, i번째 방향이 아래쪽이라면
// if (life[i] > 0 && RectDirButton[i] === "Down") {
// //scrh / 2 + i번째 ryd 값이 scrh - scrh /4 - (scrh을 32로 나눈 나머지)/2보다 작다면
// if (scrh / 2 + ryd[i] < scrh - scrh / 4 - scrh % 32 / 2) {
// //i번째 ryd값은 scrh/32만큼 증가
// ryd[i] += scrh / 32
// }
// }
// //i번째 생명이 남아있고, i번째 방향이 위쪽이라면
// if (life[i] > 0 && RectDirButton[i] === "Up") {
// //scrh / 2 + i번째 ryd 값이 (scrh을 32로 나눈 나머지)/2보다 크다면
// if (scrh / 2 + ryd[i] > scrh % 32 / 2) {
// //i번째 ryd값은 scrh/32만큼 감소
// ryd[i] -= scrh / 32
// }
// }
// //i번째 생명이 남아있고, i번째 방향이 왼쪽이라면
// if (life[i] > 0 && RectDirButton[i] === "Left") {
// //scrw / 2 + i번째 rxd 값이 (scrw을 64로 나눈 나머지)/2보다 크다면
// if (scrw / 2 + rxd[i] > scrw % 64 / 2) {
// //i번째 rxd값은 scrh/64만큼 감소
// rxd[i] -= scrw / 64
// }
// }
// //i번째 생명이 남아있고, i번째 방향이 오른쪽이라면
// if (life[i] > 0 && RectDirButton[i] === "Right") {
// //scrw / 2 + i번째 rxd 값이 scrw - scrw /8 - (scrw을 64로 나눈 나머지)/2보다 작다면
// if (scrw / 2 + rxd[i] < scrw - scrw / 8 - scrw % 64 / 2) {
// //i번째 rxd값은 scrh/64만큼 증가
// rxd[i] += scrw / 64
// }
// }
// }
//
// //아래쪽 버튼을 클릭했거나 아래쪽으로 이동중이라면
// if (start == true && DirButton === "Down" && count < 8 || start == false && count > 0 && count < 8 && DirButton === "Down") {
// //scrh / 2 + yd가 scrh - scrh /4 - (scrh를 32로 나눈 나머지)/2 보다 작다면
// if (scrh / 2 + yd < scrh - scrh / 4 - scrh % 32 / 2) {
// //count를 4로 나눈 나머지가 0이라면
// if (count % 4 == 0) {
// //yd값을 scrh/32만큼 증가
// yd += scrh / 32.toFloat()
// //n은 0
// n = 0
// //MD는 4
// MD = 4
// //count를 4로 나눈 나머지가 1 또는 3이라면
// } else if (count % 4 == 1 || count % 4 == 3) {
// //yd값을 scrh/32만큼 증가
// yd += scrh / 32.toFloat()
// //n은 1
// n = 1
// //count를 4로 나눈 나머지가 2라면
// } else if (count % 4 == 2) {
// //yd값을 scrh/32만큼 증가
// yd += scrh / 32.toFloat()
// //n은 2
// n = 2
// }
// }
// }
// //위쪽 버튼을 클릭했거나 위쪽으로 이동중이라면
// if (start == true && DirButton === "Up" && count < 8 || start == false && count > 0 && count < 8 && DirButton === "Up") {
// //scrh / 2 + yd가 (scrh를 32로 나눈 나머지)/2 보다 크다면
// if (scrh / 2 + yd > scrh % 32 / 2) {
// //count를 4로 나눈 나머지가 0이라면
// if (count % 4 == 0) {
// //yd값을 scrh/32만큼 감소
// yd -= scrh / 32.toFloat()
// //n은 6
// n = 6
// //MD는 3
// MD = 3
// //count를 4로 나눈 나머지가 1 또는 3이라면
// } else if (count % 4 == 1 || count % 4 == 3) {
// //yd값을 scrh/32만큼 감소
// yd -= scrh / 32.toFloat()
// //n은 7
// n = 7
// //count를 4로 나눈 나머지가 2라면
// } else if (count % 4 == 2) {
// //yd값을 scrh/32만큼 감소
// yd -= scrh / 32.toFloat()
// //n은 8
// n = 8
// }
// }
// }
// //왼쪽 버튼을 클릭했거나 왼쪽으로 이동중이라면
// if (start == true && DirButton === "Left" && count < 8 || start == false && count > 0 && count < 8 && DirButton === "Left") {
// //scrw / 2 + xd가 (scrw를 64로 나눈 나머지)/2 보다 크다면
// if (scrw / 2 + xd > scrw % 64 / 2) {
// //count를 4로 나눈 나머지가 0이라면
// if (count % 4 == 0) {
// //xd값을 scrw/64만큼 감소
// xd -= scrw / 64.toFloat()
// //n은 3
// n = 3
// //MD는 1
// MD = 1
// //count를 4로 나눈 나머지가 1 또는 3이라면
// } else if (count % 4 == 1 || count % 4 == 3) {
// //xd값을 scrw/64만큼 감소
// xd -= scrw / 64.toFloat()
// //n은 4
// n = 4
// //count를 4로 나눈 나머지가 2라면
// } else if (count % 4 == 2) {
// //xd값을 scrw/64만큼 감소
// xd -= scrw / 64.toFloat()
// //n은 5
// n = 5
// }
// }
// }
// //오른쪽 버튼을 클릭했거나 오른쪽으로 이동중이라면
// if (start == true && DirButton === "Right" && count < 8 || start == false && count > 0 && count < 8 && DirButton === "Right") {
// //scrw / 2 + xd가 scrw - scrw /8 - (scrw를 64로 나눈 나머지)/2 보다 작다면
// if (scrw / 2 + xd < scrw - scrw / 8 - scrw % 64 / 2) {
// //count를 4로 나눈 나머지가 0이라면
// if (count % 4 == 0) {
// //xd값을 scrw/64만큼 증가
// xd += scrw / 64.toFloat()
// //n은 9
// n = 9
// //MD는 2
// MD = 2
// //count를 4로 나눈 나머지가 1 또는 3이라면
// } else if (count % 4 == 1 || count % 4 == 3) {
// //xd값을 scrw/64만큼 증가
// xd += scrw / 64.toFloat()
// //n은 10
// n = 10
// //count를 4로 나눈 나머지가 2라면
// } else if (count % 4 == 2) {
// //xd값을 scrw/64만큼 증가
// xd += scrw / 64.toFloat()
// //n은 11
// n = 11
// }
// }
// }
// //방향키를 클릭한 상태이고, 카운트 수가 0이라면
// if (start == true && count == 0) {
// //카운트 수는 1씩 증가한다.
// count += 1
// //또한
// } else {
// //카운트 수가 0보다 크고, 8보다 작다면 카운트 수는 1씩 증가한다.
// if (count > 0 && count < 8) count += 1
// }
// //i는 0부터 3보다 작은 동안 1씩 증가한다.
// for (i in 0..2) {
// //적군의 생명이 0보다 크다면
// if (life[i] > 0) {
// //카운트 수를 1씩 증가시킨다.
// count2[i] += 1
// }
// }
// //0.1초 지연한다.
// sleep(100)
// //예외 사항
// } catch (e: Exception) {
// }
// }
// }
// }
//
// companion object {
// private val SQL_CREATE_ENTRIES =
// "CREATE TABLE " + Game.FeedEntry.TABLE_NAME + " (" +  //테이블 이름
// Game.FeedEntry._ID + " INTEGER PRIMARY KEY," +  //고유 번호 키 부여한 ID값
// Game.FeedEntry.COLUMN_NAME_TITLE + " TEXT," +  //텍스트 형식의 타이틀 이름
// Game.FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT," +
// Game.FeedEntry.COLUMN_NAME_SUBTITLE2 + " TEXT)" //텍스트 형식의 보조 타이블 이름
// private val SQL_DELETE_ENTRIES =
// "DROP TABLE IF EXISTS " + Game.FeedEntry.TABLE_NAME
// }
//
// //Game이란 이름의 생성자 생성 -> Context는 앱에 대한 다양한 정보가 들어 있다. AttributeSet은 xml정보를 가져온다.
// init {
// //부모 클래스의 생성자를 불러와서 초기화시킨다.
//
// //미디어 플레이어에서 사용할 파일 경로 연결.
// mp = MediaPlayer.create(con, R.raw.timeloop)
// //timeloop파일 재생
// mp.start()
// //timeloop파일 무한재생
// mp.isLooping = true
// }
// }