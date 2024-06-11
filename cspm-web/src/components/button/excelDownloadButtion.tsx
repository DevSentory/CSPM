'use client'
import ExcelIcon from '@/../public/icons/excelIcon.svg'
import { useSelectType, useExcelDownList } from '@/components/Zustand/store'

const BASE_URL = process.env.NEXT_PUBLIC_NEXT_APP_BASE_URL

export default function ExcelDownloadButton({ screenType }: any) {
  const { excelList } = useExcelDownList()

  const handleClick = async () => {
    const requestBody = excelList
    let apiUrl

    // screenType에 따라 requestBody와 apiUrl 분기 처리
    if (screenType === 'screen1') {
      // apiUrl = `${BASE_URL}/api/download-xlsx/describe`
      apiUrl = `${BASE_URL}/downloads/xlsx/resource`
    } else if (screenType === 'screen2') {
      // apiUrl = `${BASE_URL}/api/download-xlsx/compliance`
      apiUrl = `${BASE_URL}/downloads/xlsx/compliance`
    } else if (screenType === 'screen3') {
      // apiUrl = `${BASE_URL}/api/download-xlsx/account`
      apiUrl = `${BASE_URL}/downloads/xlsx/account`
    } else {
      console.error('Invalid screen type')
      return // apiUrl이 정의되지 않은 경우 함수 실행 중단
    }

    try {
      const response = await fetch(apiUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify(requestBody),
      })

      if (!response.ok) {
        // 응답이 성공적이지 않을 때 에러를 던짐
        throw new Error('Network response was not ok')
      }

      const blob = await response.blob()
      const url = window.URL.createObjectURL(blob)
      const a = document.createElement('a')
      a.href = url
      a.download = 'ExcelDownLoad.csv'
      document.body.appendChild(a)
      a.click()
      a.remove()
    } catch (error) {
      console.error('Fetch error', error)
    }
  }

  return (
    <button
      className='flex h-9 w-24 items-center justify-center gap-2 rounded bg-emerald-300 text-white'
      onClick={handleClick}
    >
      <ExcelIcon />
      excel
    </button>
  )
}
