'use client'

import { useEffect, useState } from 'react'

interface Props {
  showModal: boolean
  accountId: string
  scanTime: string
  accountName: string
  setShowModal: (newValue: boolean) => void
}
const BASE_URL = process.env.NEXT_PUBLIC_NEXT_APP_BASE_URL

export default function ErrLogModal({
  showModal,
  setShowModal,
  accountId,
  scanTime,
  accountName,
}: Props) {
  const handleToggleModal = () => {
    setExceptionCodes([])
    setExceptionMessages([])
    setFetchResultMessage('')
    setShowModal(false)
  }

  const [exceptionCodes, setExceptionCodes] = useState<string[]>([])
  const [exceptionMessages, setExceptionMessages] = useState<string[]>([])
  const [exceptionService, setExceptionService] = useState<string[]>([])
  const [fetchResultMessage, setFetchResultMessage] = useState('')

  const errorLogFetch = async () => {
    try {
      const response = await fetch(
        `${BASE_URL}/accounts/error/${accountId}/${scanTime}/${accountName}`,
        {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json; charset=utf-8',
          },
        },
      )

      if (response.ok) {
        const inner = await response.json()
        if (inner.code === 12) {
          setExceptionCodes([])
          setExceptionMessages([])
          setExceptionService([])
          setFetchResultMessage(inner.message)
        }
        if (inner.code === 0) {
          const data = inner.result
          setExceptionCodes(data.exceptionCodes)
          setExceptionMessages(data.exceptionMessages)
          setExceptionService(data.exceptionService)
        }
      }
    } catch (error) {
      console.error('Fail: ' + error)
    }
  }

  useEffect(() => {
    if (showModal === true) {
      errorLogFetch()
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [showModal])

  return (
    <>
      {showModal && (
        <div className='bg-dimGray fixed inset-0 z-50 flex items-center justify-center'>
          <div className=' h-[480px] w-[50%] rounded-lg bg-gray-50  p-3 shadow-md'>
            <div className=' flex justify-between  text-start'>
              <h5 className='text-lg font-bold text-black'>에러 로그 조회</h5>
              <button
                className='flex h-9 w-24 items-center justify-center rounded-md  bg-kyboNavy px-4 py-2 text-white hover:bg-gray-300'
                onClick={handleToggleModal}
              >
                취소
              </button>
            </div>
            <div className='h-4 text-sm'>{fetchResultMessage}</div>
            <div className='mt-4 grid h-[360px] w-full grid-cols-9 items-center border-2 border-gray-300'>
              <div className='col-span-3 flex h-[50px] items-center justify-center border-b-2 border-r-2 border-gray-300 bg-gray-200 font-bold text-black'>
                Error Type
              </div>
              <div className='col-span-6 flex h-[50px] items-center justify-start overflow-x-auto whitespace-nowrap border-b-2 border-gray-300 p-2'>
                {exceptionCodes.join(', ')}
              </div>
              <div className='col-span-3 flex h-[50px] items-center justify-center border-b-2 border-r-2 border-gray-300 bg-gray-200 font-bold text-black'>
                Error Servie
              </div>
              <div className='col-span-6 flex h-[50px] items-center justify-start overflow-x-auto whitespace-nowrap border-b-2 border-gray-300 p-2'>
                {exceptionService.join(', ')}
              </div>
              <div className='col-span-3 mb-1 flex h-[255px] items-center justify-center border-r-2 border-gray-300 bg-gray-200 font-bold text-black'>
                Error Response
              </div>
              <div className='col-span-6 h-[260px] flex-col items-center justify-start overflow-y-auto px-2 '>
                {exceptionMessages.map((message, index) => (
                  <span key={index}>
                    {message}
                    <br />
                  </span>
                ))}
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  )
}
