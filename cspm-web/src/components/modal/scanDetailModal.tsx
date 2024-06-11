'use client'

import Loading from '@/components/loading'
import React, { useEffect, useRef, useState } from 'react'
import { MouseEvent } from 'react'

const BASE_URL = process.env.NEXT_PUBLIC_NEXT_APP_BASE_URL
interface Props {
  showModal: boolean
  setShowModal: (newValue: boolean) => void
  resourceId: string
  scanTime: string
  accountId: string
  accountName: string
}

interface Data {
  rid: string
  scanTime: string
  policyCompliance: string
  policyType: string
  policySeverity: string
  policyTitle: string
  accountName: string
  accountId: string
  service: string
  resourceId: string
  policyDescription: string
  policyResponse: string
}

export default function ScanDetailModal({
  showModal,
  setShowModal,
  resourceId,
  scanTime,
  accountId,
  accountName,
}: Props) {
  const handleToggleModal = () => {
    setShowModal(false)
  }

  const modalRef = useRef<HTMLDivElement>(null)

  // eslint-disable-next-line react-hooks/exhaustive-deps
  const handleOutsideClick = (e: MouseEvent | Event) => {
    if (isMouseEvent(e) && modalRef.current && !modalRef.current.contains(e.target as Node)) {
      setShowModal(false)
      setData(null)
    }
  }

  const isMouseEvent = (event: MouseEvent | Event): event is MouseEvent => {
    return (event as MouseEvent).target !== undefined
  }

  // 모달이 열린 상태에서만 외부 클릭 이벤트를 등록
  useEffect(() => {
    if (showModal) {
      document.addEventListener('mousedown', handleOutsideClick as EventListener)
    } else {
      document.removeEventListener('mousedown', handleOutsideClick as EventListener)
    }
    return () => {
      document.removeEventListener('mousedown', handleOutsideClick as EventListener)
    }
  }, [handleOutsideClick, showModal])

  const [data, setData] = useState<Data | null>()
  const complianceDetailFetch = async () => {
    try {
      const response = await fetch(
        `${BASE_URL}/compliances/${resourceId}/${scanTime}/${accountId}/${accountName}`,
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
        const data = inner.result
        if (inner.code === 12) {
          setData(null)
        }
        if (data) {
          setData(data)
        }
      } else {
        setData(null)
      }
    } catch (error) {
      console.error('에러 발생 :' + error)
    }
  }

  useEffect(() => {
    if (showModal === true) {
      complianceDetailFetch()
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [showModal])

  return (
    <>
      {showModal && (
        <div className='bg-dimGray fixed inset-0 z-50 flex items-center justify-center'>
          <div
            ref={modalRef}
            className=' flex h-[90%] w-[850px] flex-col rounded-lg bg-gray-50 p-4 shadow-md'
          >
            <div className='flex h-[20px] w-full items-center justify-between text-center'>
              <h5 className='text-lg font-bold'>취약점 상세 조회</h5>
              <button
                type='button'
                className='rounded-md bg-kyboNavy px-4 py-2 text-white hover:bg-gray-300'
                onClick={handleToggleModal}
              >
                닫기
              </button>
            </div>

            {data ? (
              <div className='mt-10 h-full w-full  overflow-y-scroll border'>
                <table className='w-full border-collapse'>
                  <tbody>
                    <tr className='flex h-[35px] w-full border-b'>
                      <td className='flex h-full w-[20%] items-center justify-center bg-gray-100 font-bold'>
                        스캔 날짜
                      </td>
                      <td className='flex h-full w-[80%] items-center justify-start p-2'>
                        {data?.scanTime}
                      </td>
                    </tr>
                    <tr className='flex h-[35px] w-full border-b'>
                      <td className='flex h-full w-[20%] items-center justify-center bg-gray-100 font-bold'>
                        취약점 이름
                      </td>
                      <td className='flex h-full w-[80%] items-center justify-start p-2'>
                        {data?.policyTitle}
                      </td>
                    </tr>
                    <tr className='flex h-[35px] w-full border-b'>
                      <td className='flex h-full w-[20%] items-center justify-center bg-gray-100 font-bold'>
                        취약점 등급
                      </td>
                      <td className='flex h-full w-[80%] items-center justify-start p-2'>
                        {data?.policySeverity}
                      </td>
                    </tr>
                    <tr className='flex h-[35px] w-full border-b'>
                      <td className='flex h-full w-[20%] items-center justify-center bg-gray-100 font-bold'>
                        취약점 분류
                      </td>
                      <td className='flex h-full w-[80%] items-center justify-start p-2'>
                        {data?.policyType}
                      </td>
                    </tr>
                    <tr className='flex h-[100px] w-full items-center border-b'>
                      <td className='flex h-full w-[20%] items-center justify-center bg-gray-100 font-bold'>
                        compliance
                      </td>
                      <td className='flex h-full w-[80%] items-center justify-start text-pretty  p-2'>
                        {data?.policyCompliance}
                      </td>
                    </tr>
                    <tr className='flex h-[35px] w-full border-b'>
                      <td className='flex h-full w-[20%] items-center justify-center bg-gray-100 font-bold'>
                        RID
                      </td>
                      <td className='flex h-full w-[80%] items-center justify-start p-2'>
                        {data?.rid}
                      </td>
                    </tr>
                    <tr className='flex h-[35px] w-full border-b'>
                      <td className='flex h-full w-[20%] items-center justify-center bg-gray-100 font-bold'>
                        Account Name
                      </td>
                      <td className='flex h-full w-[80%] items-center justify-start p-2'>
                        {data?.accountName}
                      </td>
                    </tr>
                    <tr className='flex h-[35px] w-full border-b'>
                      <td className='flex h-full w-[20%] items-center justify-center bg-gray-100 font-bold'>
                        Account ID
                      </td>
                      <td className='flex h-full w-[80%] items-center justify-start p-2'>
                        {data?.accountId}
                      </td>
                    </tr>
                    <tr className='flex h-[35px] w-full border-b'>
                      <td className='flex h-full w-[20%] items-center justify-center bg-gray-100 font-bold'>
                        service
                      </td>
                      <td className='flex h-full w-[80%] items-center justify-start p-2'>
                        {data?.service}
                      </td>
                    </tr>
                    <tr className='flex h-[35px] w-full border-b'>
                      <td className='flex h-full w-[20%] items-center justify-center bg-gray-100 font-bold'>
                        Resource ID
                      </td>
                      <td className='flex h-full w-[80%] items-center justify-start p-2'>
                        {data?.resourceId}
                      </td>
                    </tr>
                    <tr className='flex h-[150px] w-full border-b'>
                      <td className='flex h-full w-[20%] items-center justify-center bg-gray-100 font-bold'>
                        취약점 설명
                      </td>
                      <td className='flex h-full w-[80%] items-center justify-start text-pretty p-2'>
                        {data?.policyDescription}
                      </td>
                    </tr>
                    <tr className='flex h-[200px] w-full'>
                      <td className='flex h-full w-[20%] items-center justify-center bg-gray-100 font-bold'>
                        대응 방안
                      </td>
                      <td className='flex h-full w-[80%] items-center justify-start text-pretty p-2'>
                        {data?.policyResponse}
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            ) : (
              <div className='flex h-full w-full items-center justify-center'>
                <Loading />
              </div>
            )}
          </div>
        </div>
      )}
    </>
  )
}
