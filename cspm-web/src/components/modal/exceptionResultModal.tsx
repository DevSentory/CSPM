'use client'

import React, { useState, useEffect, useRef } from 'react'

const BASE_URL = process.env.NEXT_PUBLIC_NEXT_APP_BASE_URL
interface Props {
  showResModal: boolean
  setShowResModal: (newValue: boolean) => void
  resourceId: string
  policyTitle: string
  accountId: string
  accountName: string
}

interface Data {
  exceptionTime: string
  exceptionContent: string
  exceptionHandler: string
}

function ExceptionResultModal({
  showResModal,
  setShowResModal,
  resourceId,
  policyTitle,
  accountId,
  accountName,
}: Props) {
  const [data, setData] = useState<Data | null>()
  const exceptionResultFetch = async () => {
    try {
      const response = await fetch(
        `${BASE_URL}/compliances/exception/${resourceId}/${policyTitle}/${accountId}/${accountName}`,
        {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json; charset=utf-8',
          },
          credentials: 'include',
        },
      )
      if (response.ok) {
        const inner = await response.json()
        if (inner.code === 12) {
          setData(null)
        }
        if (inner.code === 0) {
          const data = inner.result
          setData(data)
        }
      }
    } catch (error) {
      console.error('error: ' + error)
    }
  }

  const handleCloseModal = () => {
    setShowResModal(false)
  }
  const modalRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    if (showResModal == true) {
      exceptionResultFetch()
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [showResModal])
  return (
    <>
      {showResModal && (
        <div className='bg-dimGray fixed inset-0 z-50 flex items-center justify-center'>
          <div ref={modalRef} className='h-[300px] w-[500px] rounded-lg bg-gray-50 p-4 shadow-md'>
            <div className='mb-4 text-center'>
              <h5 className='text-lg font-bold'>Exception 결과</h5>
            </div>
            <hr />

            <table className='w-full border-collapse'>
              <tbody>
                <tr className='flex h-[50px] w-full border-b'>
                  <td className='flex h-full w-[30%] items-center justify-center bg-gray-100 font-bold'>
                    예외처리 시간
                  </td>
                  <td className='flex h-full w-[80%] items-center justify-start p-2'>
                    {data?.exceptionTime}
                  </td>
                </tr>
                <tr className='flex h-[50px] w-full border-b'>
                  <td className='flex h-full w-[30%] items-center justify-center bg-gray-100 font-bold'>
                    예외처리 내용
                  </td>
                  <td className='flex h-full w-[80%] items-center justify-start p-2'>
                    {data?.exceptionContent}
                  </td>
                </tr>
                <tr className='flex h-[50px] w-full border-b'>
                  <td className='flex h-full w-[30%] items-center justify-center bg-gray-100 font-bold'>
                    처리자
                  </td>
                  <td className='flex h-full w-[80%] items-center justify-start p-2'>
                    {data?.exceptionHandler}
                  </td>
                </tr>
              </tbody>
            </table>
            <div className='mt-4 text-right'>
              <button
                type='button'
                className='rounded-md bg-gray-200 px-4 py-2 hover:bg-gray-300'
                onClick={handleCloseModal}
              >
                닫기
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  )
}

export default ExceptionResultModal
